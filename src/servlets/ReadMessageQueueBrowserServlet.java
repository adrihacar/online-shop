package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.ChatBean;
import entities.ChatDAOImpl;
import entities.ChatElement;
import entities.ChatMessage;
import entities.UserBean;
import jdbc.UserDAOImp;

/**
 * Servlet implementation class ReadMessageQueueBrowserServlet
 */
@WebServlet(
		urlPatterns= {"/chatroom", "/swapchat"},
		loadOnStartup=1,
		initParams={@WebInitParam(name="configuracion", value="tiw.p1.onlineShop")}
		)
public class ReadMessageQueueBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHATROOM_JSP = "/chatRoom.jsp";
	private static final String ERROR_JSP = "/errorPage.jsp";
	private static final String PERSISTENCE_UNIT = "online_shop";
	private static final String SWAP_CHAT = "/swapchat";
	private static final String LOAD_ALL_CHATS = "/chatroom";
	private ServletConfig config;
	private long activeChatID = -1;
	private int loggedUserID;
	/**
	 * Auxiliary variable. Id of the user recipient of the messages.
	 */
	private int recipientUser = -1;

	// Inject the Connection Factory
	@Resource(mappedName = "ChatRoomFactory") //logic name
	private ConnectionFactory chatRoomFactory;

	// Inject the queue
	@Resource(mappedName = "ChatRoomQueue")
	private Queue queue;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReadMessageQueueBrowserServlet() {
		super();        
	}

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	

		try {			
			doPost(request, response);
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doGet: " + e);
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);;
		}		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		try {	
			//Obtain the logged user's id (user_id) from the session
			HttpSession oHttpSession = request.getSession();
			loggedUserID = (int) oHttpSession.getAttribute("user_id");
			String servletPath = request.getServletPath();

			if(servletPath.equals(LOAD_ALL_CHATS)) {
				//Case: LOAD_ALL_CHATS: Update both the chatList and the activeChat

				/*
				 * The attribute 'sendTo' is only set when accessing the chat room after clicking 'direct message' button
				 * Otherwise, the user only enters the chat room without preselecting any recipient user.
				 * */ 
				try {
					recipientUser = (int) request.getAttribute("sendTo");
				} catch (Exception e) {
					//The attribute is not defined in this request. Ignore
					recipientUser = -1;
				}			

				List<ChatBean> userChats = getUserChats();
				List<ChatElement> chatList = generateChatList(userChats);

				//Update the attributes from the session
				oHttpSession.setAttribute("chatList", chatList);
				oHttpSession.setAttribute("activeChat", activeChatID);

				//Update the messages displayed
				//List<ChatMessage> activeChatMsgs = getActiveChatMessages();
				List<String> activeChatHtml = getMsgHistoryHtml(getActiveChatMessages());

				//Set the attributes of the new request					
				request.setAttribute("msgList", activeChatHtml);

				//response.setIntHeader("Refresh", 1);
				config.getServletContext().getRequestDispatcher(CHATROOM_JSP).forward(request, response);

			}else if(servletPath.equals(SWAP_CHAT)){ //Case: SWAP_CHAT: update only activeChat
				/*This URL is only triggered by the AJAX event.
				 * To simplify the drawing of the page in the JS file,
				 * the HTML code is sent directly to the page, to replace the previous element
				 * */
				activeChatID = (long) request.getAttribute("chatRef");

				//List<ChatMessage> activeChatMsgs = getActiveChatMessages();
				List<String> activeChatHtml = getMsgHistoryHtml(getActiveChatMessages());

				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println(activeChatHtml);

			}else {
				//Case: no valid servletPath
				throw new Exception("invalid servletPath");
			}
		} catch (Exception e) {			
			System.out.println("UNEXPECTED ERROR in doPost: ");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 
	 * @param activeChat ArrayList of ChatMessage objects
	 * @return A List containing the generated HTML from each message
	 */
	private List<String> getMsgHistoryHtml(List<ChatMessage> activeChat) {
		List<String> activeChatHtml = new ArrayList<String>();
		for (int i = 0; i < activeChat.size(); i++){

			boolean isLastMsg = false;
			ChatMessage msg = activeChat.get(i);

			if(i == activeChat.size() - 1){	isLastMsg = true; }
			activeChatHtml.add(msg.toHtml(loggedUserID, isLastMsg));
		}
		return activeChatHtml;
	}

	/**
	 * Retrieves all the chats where the user is participating
	 * @return Array list containing the chats of the user, ordered by last message delivery time
	 * */
	private List<ChatBean> getUserChats() throws ServletException, IOException {
		List<ChatBean> userChats;

		try {				
			ChatDAOImpl chatDAO = new ChatDAOImpl(PERSISTENCE_UNIT);			
			userChats = chatDAO.getChatsByUser(loggedUserID);

			//Check if the user has any chat ()
			if(!userChats.isEmpty()) {
				/* If recipientUser is set, the logged user is trying to access directly to an specific chat.
				 * Find that chat, involving both the logged user and the recipientUser, if exists. 
				 * If it is not found create a new Chat instance.
				 * */
				if(recipientUser != -1) {
					activeChatID = existChatWithUser(userChats, recipientUser);
					if(activeChatID == -1) {				
						//chat not found. The logged user is starting a new chat
						//Create a new chat (locally) and add it to the local chatList (NOT COMMITED TO THE DB YET)
						ChatBean oNewChat = new ChatBean(activeChatID, loggedUserID, recipientUser, "");
						userChats.add(0, oNewChat);
					}
				}else {
					//No recipientUser specified, the active chat will be, by default, the chat with the most recent message
					ChatBean activeChat = userChats.get(0);
					activeChatID = activeChat.getChatID();
					recipientUser = activeChat.getBuyer() == loggedUserID ? activeChat.getSeller()
							: activeChat.getBuyer();
				}
			}
						
		} catch (Exception e) {
			System.out.println("ERROR in getUserChats(): chats could not be retireved from the DB [" + e.getMessage() +"]");			
			userChats = null;
		}
		return userChats;

	}

	/**
	 * Creates a list of ChatElement objects based on a collection of ChatBeans
	 * @param userChats List object containing the ChatBeans
	 * @return a List object containing the chatElements created from the input. Null if there was any error
	 * @throws Exception 
	 */
	private List<ChatElement> generateChatList(List<ChatBean> userChats) throws Exception {

		List<ChatElement> chatList = new Vector<ChatElement>();	
		if(!userChats.isEmpty()) {
			try {

				//Create a connection using the connectionFactory
				Connection oConn = chatRoomFactory.createConnection();
				// Next create the session. Indicate that transaction will not be supported
				Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

				//For each ChatBean, create a ChatElement
				for (ChatBean chat : userChats) {

					ChatElement chatElem = new ChatElement(chat);
					UserDAOImp userDAO = new UserDAOImp();
					String chatType = "";
					//Get the data from the other user participating in the chat, that is not the logged user
					UserBean user;
					if(chat.getBuyer() == loggedUserID) {
						user = userDAO.getUserdata(chat.getSellerString());
						chatType = ChatElement.CHAT_TO_BUY;
					}else {
						user = userDAO.getUserdata(chat.getBuyerString());
						chatType = ChatElement.CHAT_TO_SELL;
					}					

					//Get the JMSMessageID of the current chat
					String lastMsgId = chat.getLastMsgId();


					//Create the message selector		     		     
					String selector = "JMSMessageID = " + lastMsgId;		      
					// Create a browser to retrieve selected message
					QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);		      
					oConn.start();

					// User the browser to retrieve a collection (enumeration) of messages
					@SuppressWarnings("rawtypes")
					java.util.Enumeration enum1 = oBrowserConsumer.getEnumeration();

					String lastMsgTxt = "";
					long lastMsgDate = 0L;

					//enum1 should only contain one element, thus the loop is broken after reading the first message 
					while (enum1.hasMoreElements()) {
						// Get the first message from the enumeration
						Object oMessage = enum1.nextElement();

						// Check if the message is an instance of TextMessage
						if (oMessage instanceof TextMessage) {
							//Get the interesting properties from the message
							lastMsgTxt = (String) ((TextMessage) oMessage).getText();
							lastMsgDate = ((TextMessage) oMessage).getJMSTimestamp();							
							break;
						}else {
							//If the message is not a valid the whole search is stopped
							throw new JMSException("The message " + lastMsgId + " is not a TextMessage");
						}							
					}

					//Set the values of the chatElem
					chatElem.setRecipientUser(user.getName() + " " + user.getSurname());
					chatElem.setLastMsgText(lastMsgTxt);
					chatElem.setLastMsgDate(lastMsgDate);
					chatElem.setChatType(chatType);

					// Close the browser created for the current chat
					oBrowserConsumer.close();			

				}//End of loop

				// Close session
				oSession.close();
				// Close connection
				oConn.close();
			} catch (Exception e) {
				System.out.println("UNEXPECTED ERROR in getChatList: " + e.getMessage());
				e.printStackTrace();
				chatList = null;
			}		

		}
		return chatList;		
	}

	/**
	 * Retrieve a list with all the messages between two users
	 * This implementation does not consider storing messages in the DB, only in the server
	 * @return A List object containing all the messages corresponding to the chat 'activeChatId'
	 * @throws ServletException
	 * @throws IOException
	 */
	private List<ChatMessage> getActiveChatMessages() throws ServletException, IOException {

		try {

			// Create a connection using the connectionFactory
			Connection oConn = chatRoomFactory.createConnection();
			// Next create the session. Indicate that transaction will not be supported
			Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Create the message selector		     		     
			String selector = "chatID = " + String.valueOf(activeChatID);		      
			// USe the session to create a browser associated to the queue
			QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);		      
			oConn.start();

			List<ChatMessage> activeChat = new ArrayList<ChatMessage>();
			
			// User the browser to retrieve a collection (enumeration) of messages
			@SuppressWarnings("rawtypes")
			java.util.Enumeration enum1 = oBrowserConsumer.getEnumeration();

			

			if (enum1.hasMoreElements()) {				

				while (enum1.hasMoreElements()) {
					// Get a message from the enumeration
					Object oMessage = enum1.nextElement();

					// Check if the message is an instance of TextMessage
					if (oMessage instanceof TextMessage) {
						//Add the msg to the list						
						activeChat.add(new ChatMessage(oMessage));		
					}										
				}
			}

			// Close browser
			oBrowserConsumer.close();			
			// Close session
			oSession.close();
			// Close connection
			oConn.close();

			return activeChat;

		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in getActiveChat: " + e.getMessage());	
			e.printStackTrace();
			return null;
		}				
	}
	/**
	 * Checks if exist a chat between the logged user and the one specified.
	 * if the chat exist within Chatlist, then returns the corresponding chatID
	 * @param userChats List containing all the chats involving the logged user
	 * @param userId User recipient of the messages
	 * @return the chatID of the chat between the logged user and the specified user. 
	 * -1 if no coincidence was found
	 */
	private long existChatWithUser(List<ChatBean> userChats, int userId) {

		for (ChatBean chat : userChats) {
			if(chat.isBetween(loggedUserID, userId)) {
				return chat.getChatID();
			}					
		}
		return -1L;

	}

}
