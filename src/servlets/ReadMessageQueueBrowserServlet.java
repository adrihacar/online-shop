package jhc.jms;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
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

import com.sun.research.ws.wadl.Request;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import beans.ChatBean;
import beans.ChatMessage;

/**
 * Servlet implementation class ReadMessageQueueBrowserServlet
 */
@WebServlet(
		urlPatterns="/ReadMessageQueueBrowser.html",
		loadOnStartup=1,
		initParams={@WebInitParam(name="configuracion", value="tiw.p1.onlineShop")}
		)
public class ReadMessageQueueBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHATROOM_JSP = "/chatRoom.jsp";
	private static final String ERROR_JSP = "/ErrorPage.jsp";
	private ServletConfig config;
	private long activeChatID;
	//private List<ChatMessageBean> activeChat;
	//private List<ChatBean> chatList;
    
	 // Inject the connectionFactory using annotations
	 //.. . .
	@Resource(mappedName = "tiwconnectionfactory") //logic name
	 private ConnectionFactory tiwconnectionfactory;
	 // Inject the queue using annotations
	 //. . . 
	 @Resource(mappedName="tiwqueue") //logic name
	 private Queue queue;
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadMessageQueueBrowserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {
    	this.config = config;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			//config.getServletContext().getRequestDispatcher(CHATROOM_JSP).forward(request, response);
			doPost(request, response);
		} catch (Exception e) {
			System.out.println(
				"JHC ***************************************Error in doGet: "
					+ e);
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);;
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		try {

			/*
			// Create a connection using the connectionFactory
		      Connection oConn = tiwconnectionfactory.createConnection();
		      // Next create the session. Indicate that transaction will not be supported
		      Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		      
		      //Query the DB to obtain the chatID of the active chat
		      //SELECT conv.ID FROM chatList WHERE (conv.user==me AND conv.active)
		      
		      //Create the message selector
		      //String selector = chatID; //BigInteger.valueOf(chatID);
		      
			// USe the session to create a browser associated to the queue
		      //QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);
		      QueueBrowser oBrowserConsumer = oSession.createBrowser(queue);
			//Start the connection
		      oConn.start();
			// User the browser to retrieve a collection (enumeration) of messages
			java.util.Enumeration enum1 = oBrowserConsumer.getEnumeration();
			
			ArrayList<ChatMessageBean> activeChat = new ArrayList<ChatMessageBean>();
			
			while (enum1.hasMoreElements()) {
				// Get a message from the enumeration
				Object oMessage = enum1.nextElement();
				
				ChatMessageBean msg = new ChatMessageBean();
				
				// Check if the message is an instance of TextMessage
				if (oMessage instanceof TextMessage) {
					
					//Set the message
					msg.setMsg(((TextMessage) oMessage).getText());
					//Set the delivery time
					msg.setDeliveryTime(((TextMessage) oMessage).getJMSDeliveryTime());
					//Set the sender
					msg.setSenderID(((TextMessage) oMessage).getLongProperty("sender"));
					//out.println("<p>" + message + "</p>");
					
					//Add the msg to the list
					activeChat.add(msg);		
				}
				// If it is an instance of TextMessage, cast it and add it to the out
			}
			*/
			//Add the object to the request and send it
			//request.setAttribute("msgList", activeChat);
			Boolean createNewChat = false;
			createNewChat = (Boolean) request.getAttribute("newChat"); 
			if(createNewChat) {
				Integer buyer = (Integer) request.getAttribute("buyer");
				Integer seller = (Integer) request.getAttribute("seller");
				
				entityManager.getTransaction().begin();
				//call JPA to add a new row in the table
				ChatBean newChat = new ChatBean();
				newChat.setChatID(chatID);//PK set by JPA
				newChat.setBuyer(buyer.intValue());
				newChat.setSeller(seller.intValue());
				
				entityManager.persist(newChat);
				
				//Set the new chat as the active chat
				activeChatID = newChat.getChatID();
				
				entityManager.getTransaction().commit();
				
			}
			
			ArrayList<ChatBean> chatList;
			Object oChatList = getAllChats();
			
			if(oChatList != null) {
				//If the object is not null, then cast it to an ArrayList
				chatList = (ArrayList<ChatBean>) (oChatList);
				/*If no new conversation has been created, the active 
					chat is the most recent one, the first in the list returned by JPA
				*/
				if (!createNewChat) {
					activeChatID = chatList.get(0).getChatID();
				}
			}else {
				chatList = null;
			}
			request.setAttribute("chatList", chatList);
			HttpSession oHttpSession = request.getSession();
			oHttpSession.setAttribute("activeChat", activeChatID);
			
			
			ArrayList<ChatMessage> activeChat;
			Object oChat = getActiveChat();
			
			if(oChat != null) {
				//If the object is not null, then cast it to an ArrayList
				activeChat = (ArrayList<ChatMessage>) (oChat);
			}else {
				activeChat = null;
			}
			
			request.setAttribute("msgList", activeChat);
			//response.setIntHeader("Refresh", 1);
			config.getServletContext().getRequestDispatcher(CHATROOM_JSP).forward(request, response);
			
			/*This is not longer necessary. See getActiveChat()
			// Stop connection
			oConn.stop();
			// Close browser
			oBrowserConsumer.close();
			// Close session
			oSession.close();
			// Close connection
			oConn.close();
			*/
			
		} catch (Exception e) {
			System.out.println(
				"JHC *************************************** Error in doPost: "
					+ e);
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}
	/**
	 * Retrieves all the chats where the user is participating
	 * @return Array list containing the chats of the user, ordered by last message delivery time
	 * */
	private ArrayList<ChatBean> getAllChats() throws ServletException, IOException {
		ArrayList<ChatBean> chatList = new ArrayList<ChatBean>();
		
		try {
			
			
			/*
	    	 * Call JPA to perform:
	    		SELECT chat FROM chatList WHERE chat.buyer == session.myID OR chat.seller == session.myID ORDER BY chat.lastMsg.Date
	    	Query query = entityManger.createNamedQuery("findChatsByUser");
	    	query.setParameter("user", session.myId);
	    	List<ChatBean> chatList = query.getResultList();
	    	
	    	if (oChats != null) {
				chatList = (List<ChatBean>) oChats;
			}else {
				chatList = null;
			}
	    	 * 
	    	 */
			
			return chatList;
		} catch (Exception e) {
			System.out.println(
					"JHC *************************************** Error in getAllChats(): "
						+ e);
			return null;
		}
		
	}
	
	/**
	 * Retrieve a list with all the messages between two users
	 * THIS IMPLEMENTATION DOES NOT CONSIDER MESSAGES STORED IN THE DB, ONLY IN THE SERVER
	 * */
	private List<ChatMessage> getActiveChat() throws ServletException, IOException {
				
		try {

			// Create a connection using the connectionFactory
		      Connection oConn = tiwconnectionfactory.createConnection();
		      // Next create the session. Indicate that transaction will not be supported
		      Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		      
		      //NOT NECESSARY NOW
		      //Query the DB to obtain the chatID of the active chat
		      //myID = getAttribute("ID")
		      //SELECT chat.ID FROM chatList WHERE (conv.user==me AND conv.ID = user.activeChat)
		      // or directly SELECT chat.ID FROM chatList WHERE chat.ID == user.activeChat
		      
		      
		      //Create the message selector		     		     
		      String selector = "chatID = " + String.valueOf(activeChatID);		      
			// USe the session to create a browser associated to the queue
		      QueueBrowser oBrowserConsumer = oSession.createBrowser(queue, selector);
		      //QueueBrowser oBrowserConsumer = oSession.createBrowser(queue);
			//Start the connection
		      oConn.start();
			// User the browser to retrieve a collection (enumeration) of messages
			@SuppressWarnings("rawtypes")
			java.util.Enumeration enum1 = oBrowserConsumer.getEnumeration();
			
			ArrayList<ChatMessage> activeChat = null;
			
			if (enum1.hasMoreElements()) {
				activeChat = new ArrayList<ChatMessage>();
				
				while (enum1.hasMoreElements()) {
					// Get a message from the enumeration
					Object oMessage = enum1.nextElement();
					
					ChatMessage msg = new ChatMessage();
								
					
					// Check if the message is an instance of TextMessage
					if (oMessage instanceof TextMessage) {
						
						//Set the message
						msg.setMsg(((TextMessage) oMessage).getText());
						//Set the delivery time						
						Long deliveryTime;
						try {
							//deliveryTimo = ((TextMessage) oMessage).getLongProperty("deliveryTime");
							deliveryTime = ((TextMessage) oMessage).getJMSTimestamp();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("ERROR: the delivery timestamp of this message is not valid");
							deliveryTime = 0L;
						}
						
						msg.setDeliveryTime(deliveryTime.longValue());
						
						//Set the sender
						Integer iSenderID;
						try {
							iSenderID = ((TextMessage) oMessage).getIntProperty("senderID");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("ERROR: This message has no valid senderID");
							//iSenderID = 1313;
						}																		
						System.out.println("**************************READ MESSAGE FROM USER " + iSenderID);
						msg.setSenderID(iSenderID.intValue()); 												
						
						
						//Add the msg to the list						
						activeChat.add(msg);		
					}
					// If it is an instance of TextMessage, cast it and add it to the out
					
				}
			}
			
			
								
			// Stop connection 
			//oConn.stop(); //GENERATES a JMSException
			// Close browser
			oBrowserConsumer.close();
			
			// Close session
			oSession.close();
			// Close connection
			oConn.close();
			
			
			return activeChat;
			
		} catch (Exception e) {
			System.out.println(
				"JHC *************************************** Error in getActiveChat: ");
			e.printStackTrace();
			return null;
		}				
	}
	
}
