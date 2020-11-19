package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.ChatBean;
import entities.ChatDAOImpl;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;


/**
 * Servlet implementation class SendMessageQueueServlet
 */
@WebServlet(urlPatterns = {"/SendMessage"})
public class SendMessageQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHATROOM_PAGE = "/chatroom";
	private static final String ERROR_JSP = "/errorPage.jsp";
	private static final String PERSISTENCE_UNIT = "online_shop";
	private ServletConfig config;
	private int loggedUserId;
	private long activeChatID;

	// Inject the Connection Factory
	@Resource(mappedName = "ChatRoomFactory") //logic name
	private ConnectionFactory chatRoomFactory;

	// Inject the queue
	@Resource(mappedName = "ChatRoomQueue")
	private Queue queue;



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessageQueueServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			ChatBean activeChat = new ChatBean();

			//Obtain attributes from the session
			HttpSession oHttpSession = request.getSession();
			loggedUserId = (int) oHttpSession.getAttribute("user_id");					
			activeChatID = (long) oHttpSession.getAttribute("activeChat");

			//Obtain attributes from the request
			//Id of the user recipient of the messages.
			int recipientUser = (int) request.getAttribute("sendTo");


			//Create a message Producer using the predefined Connection Factory
			Connection oConn = chatRoomFactory.createConnection();
			Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer oProducer = oSession.createProducer(queue);

			//Create DAO
			ChatDAOImpl chatDAO = new ChatDAOImpl(PERSISTENCE_UNIT);

			//If active chat == -1 --> is a new chat
			if(activeChatID == -1) {
				//Create a new ChatBean instance and call JPA
				ChatBean oNewChat = new ChatBean();
				//oNewChat.setChatID(chatID);//THIS ID should be only set by the JPA
				oNewChat.setBuyer(loggedUserId);
				oNewChat.setSeller(recipientUser);
				oNewChat.setLastMsgId("");

				chatDAO.insert(oNewChat);

				activeChatID = oNewChat.getChatID();
				activeChat = oNewChat;


			}else {
				//The chat already exist. Retrieve it from the DB
				activeChat = chatDAO.getChatById(activeChatID);
			}

			//Create the message
			TextMessage txtMsg = oSession.createTextMessage();

			//Retrieve the parameter 'message' from the request, and use it as text of our message
			txtMsg.setText(request.getParameter("message"));				

			//Set additional properties of the message			
			txtMsg.setLongProperty("chatID", activeChatID);			
			txtMsg.setIntProperty("senderID", loggedUserId);								

			// Use the message producer to send the message	
			oProducer.send(txtMsg);

			//Update the lastMessageId value in the DB
			activeChat.setLastMsgId(txtMsg.getJMSMessageID());
			chatDAO.update(activeChat);

			// Close the producer
			oProducer.close();
			// Close the session 
			oSession.close();
			// Close the connection 
			oConn.close();

			//Redirect
			config.getServletContext().getRequestDispatcher(CHATROOM_PAGE).forward(request, response);

		} catch (JMSException e) {
			System.out.println("UNEXPECTED ERROR with the Message Queue while sending the message [" + e.getMessage() + "]");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());			
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);

		}catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doPost: message not sent [" + e.getMessage() + "]");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//Call doPost instead
			doPost(request, response);
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doGet: " + e.getMessage() + "]");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}

	}

}
