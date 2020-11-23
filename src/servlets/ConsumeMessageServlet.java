package servlets;

import java.io.IOException;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReadMessageQueueServlet
 */
@WebServlet({ "/deletemessages" })
public class ConsumeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	private static final String CHATROOM_JSP = "/chatRoom.jsp";
	private static final String ERROR_JSP = "/ErrorPage.jsp";

    
	// Inject the Connection Factory
	@Resource(mappedName = "ChatRoomFactory") //logic name
	 private ConnectionFactory chatRoomFactory;
	
	 // Inject the queue
	@Resource(mappedName = "ChatRoomQueue")
	 private Queue queue;
	 
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsumeMessageServlet() {
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

		try {
			doPost(request, response);
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doGet");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
			
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			

		try {
		
			// Create a connection using the connectionFactory
		      Connection oConn = chatRoomFactory.createConnection();
		      // Next create the session. Indicate that transaction will not be supported
			Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
					
			//Start connection
			oConn.start();
			// USe the session to create a consumer
			MessageConsumer oConsumer = oSession.createConsumer(queue);			
			Message txtMessage = null;
			while (true) {
				// Use the message consumer to try to retrieve a message. Timing 1000
				txtMessage = oConsumer.receive(1000);
				if (txtMessage != null) {
					// Check if the message is an instance of TextMessage
					if (txtMessage instanceof TextMessage) {
						TextMessage receivedMessage = (TextMessage) txtMessage;
						System.out.println("Message consumed ["+ receivedMessage.getJMSMessageID() +"]: " + receivedMessage.getText());					
					}					
					// If it is an instance of TextMessage, cast it and add it to the out
				} else // there are no messages
					{					
					break;
				}

			}

			//Close the session
			oSession.close();
			//Close the connection
			oConn.close();
			
			config.getServletContext().getRequestDispatcher(CHATROOM_JSP).forward(request, response);
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doPost");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
			
		}
			
	}

}
