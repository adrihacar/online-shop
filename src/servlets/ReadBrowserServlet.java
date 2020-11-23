package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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

import entities.ChatMessage;
import entities.UserChat;
import utils.ChatList;

/**
 * Servlet implementation class ReadMessageQueueBrowserServlet
 */
@WebServlet(
		urlPatterns= {"/chatroom", "/swapchat", "/reloadchat"},
		loadOnStartup=1,
		initParams={@WebInitParam(name="configuracion", value="tiw.p1.onlineShop")}
		)
public class ReadBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHATROOM_JSP = "/chatRoom.jsp";
	private static final String ERROR_JSP = "/errorPage.jsp";
	
	private static final String SWAP_CHAT = "/swapchat";
	private static final String LOAD_ALL_CHATS = "/chatroom";	
	private ServletConfig config;
	
	
	// Inject the Connection Factory
		@Resource(mappedName = "ChatRoomFactory") //logic name
		private ConnectionFactory chatRoomFactory;

		// Inject the queue
		@Resource(mappedName = "ChatRoomQueue")
		private Queue queue;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReadBrowserServlet() {
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

			String servletPath = request.getServletPath();

			Object atr = oHttpSession.getAttribute("user_id");
			int loggedUser;
			if (atr == null) {
				throw new NullPointerException();
			}

			loggedUser = (int) atr;
						
			
			//Generating the chat list of the logged user
			ChatList<UserChat> userChats = new ChatList<UserChat>(loggedUser);
			//Retrieving chat info from the database
			userChats.retrieveChatsInfo();
			//Retrieve the last message from each existent chat 
			for (UserChat chat : userChats) {
				TextMessage msg = this.getMessageById(chat.getLastMsgId());
				if(msg != null) {
					chat.updateLastMsg(msg);
				}								
			}

			if(servletPath.equals(LOAD_ALL_CHATS)) {
				//Case: LOAD_ALL_CHATS: Update both the chatList and the activeChat

				/*
				 * The attribute 'sendTo' is only set when accessing the chat room after clicking 'direct message' button
				 * Otherwise, the user only enters the chat room without preselecting any recipient user.
				 * */ 				
				atr = request.getParameter("sendTo");
				
				int recipientUser;
				if (atr != null) {
					recipientUser = Integer.parseInt((String) atr);					
					userChats.swapActiveChat(recipientUser);					
					
				}
				
				//Retrieve (only) the messages of the active chat, if any
				if(!userChats.isEmpty()) {
					ArrayList<ChatMessage> msgHistory = this.retrieveChatMessages(userChats.getActiveChat().getChatId());					
					//Remove any other chat history currently loaded in the list
					userChats.purge();
					userChats.getActiveChat().loadMessages(msgHistory);
				}
								
				//Send the chats
				oHttpSession.setAttribute("chats", userChats);

				System.out.println("******************* number of chats active = " + userChats.size() );				
				
				config.getServletContext().getRequestDispatcher(CHATROOM_JSP).forward(request, response);

			}else if(servletPath.equals(SWAP_CHAT)){ //Case: SWAP_CHAT: update only activeChat
				
				/*This URL is only triggered by the AJAX event.
				 * To simplify the drawing of the page from the JS file,
				 * the HTML code generated is sent directly to the page, to replace the previous element
				 * */
				//Retrieve the chat with the requested user			
				long activeChatID = Long.parseLong(request.getParameter("chatRef"));
				UserChat openedChat = userChats.getChatById(activeChatID);
				userChats.purge();
				openedChat.loadMessages(retrieveChatMessages(activeChatID));
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(openedChat.printMsgHistoryHtml());				

			}else {
				//Case: no valid servletPath
				throw new Exception("invalid action");
			}
		} catch (Exception e) {			
			System.out.println("UNEXPECTED ERROR in doPost: ");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}
	
	
	private TextMessage getMessageById(String msgId) throws Exception {
				
		//Create a connection using the connectionFactory		
		Connection oConn = chatRoomFactory.createConnection();
				
		// Next create the session. Indicate that transaction will not be supported
		Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		//Create the message selector		     		     
		String selector = "JMSMessageID = '" + msgId + "'";		      
		// Create a browser to retrieve selected message
		QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);		      
		oConn.start();

		// User the browser to retrieve a collection (enumeration) of messages
		@SuppressWarnings("rawtypes")
		java.util.Enumeration chatMessages = oBrowserConsumer.getEnumeration();


		//enum1 should only contain one element, since JMSMessageID should be unique 
		Object oMessage = chatMessages.nextElement();
		TextMessage result = null;
			// Check if the message is an instance of TextMessage
			if (oMessage instanceof TextMessage) {
				result = (TextMessage) oMessage;				
			}							
		return result;
	}
	
	/**
	 * Retrieve a list with all the messages between two users
	 * This implementation does not consider storing messages in the DB, only in the server
	 * @param chatId chat whose messages are requested
	 * @return An ArrayList of ChatMessage objects, containing all the messages corresponding to the chat 'activeChatId'
	 * @throws Exception 
	 */
	private ArrayList<ChatMessage> retrieveChatMessages(long chatId) throws Exception {
		ArrayList<ChatMessage> msgHistory = new ArrayList<ChatMessage>();
		try {

			// Create a connection using the connectionFactory
			Connection oConn = chatRoomFactory.createConnection();
			// Next create the session. Indicate that transaction will not be supported
			Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);			
			//Create the message selector		     		     
			String selector = "chatID = " + String.valueOf(chatId);		      
			// USe the session to create a browser associated to the queue
			QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);		      
			oConn.start();			
			
			// User the browser to retrieve a collection (enumeration) of messages
			@SuppressWarnings("rawtypes")
			java.util.Enumeration enum1 = oBrowserConsumer.getEnumeration();		
					
				while (enum1.hasMoreElements()) {					
					// Get a message from the enumeration
					Object oMessage = enum1.nextElement();

					// Check if the message is an instance of TextMessage
					if (oMessage instanceof TextMessage) {
						//Add the msg to the list						
						msgHistory.add(new ChatMessage(oMessage));		
					}										
				}
			
			// Close browser
			oBrowserConsumer.close();			
			// Close session
			oSession.close();
			// Close connection
			oConn.close();
			
			return msgHistory;
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR retrieving the chat messages");	
			e.printStackTrace();
			throw e;
		}				
		
		
	}
}
