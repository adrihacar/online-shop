package jhc.jms;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jrockit.jfr.Producer;

import beans.ChatMessage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;


/**
 * Servlet implementation class SendMessageQueueServlet
 */
@WebServlet(urlPatterns = {"/SendMessageQueue.html"})
public class SendMessageQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;

	 // Inject the connectionFactory using annotations
	 //.. . .
	@Resource(mappedName = "tiwconnectionfactory") //logic name
	 private ConnectionFactory tiwconnectionfactory;
	 // Inject the queue using annotations
	 //. . . 
	@Resource(mappedName = "tiwqueue") //logic name
	 private Queue queue;
	 
	 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessageQueueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	this.config = config;
    }

			
	public void doPost(
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
			
		/*
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head><title>Sending message to a queue</title></head>");
			out.println("<body>");
			out.println("<H1><U>Sending the message</U></H1>");
			*/

			try {

				
				// - In the following steps we write the message and send it				
				// First create a connection using the connectionFactory
			      Connection oConn = tiwconnectionfactory.createConnection();
			      // Next create the session. Indicate that transaction will not be supported
					Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				// Now use the session to create a message producer associated to the queue
					MessageProducer oProducer = oSession.createProducer(queue);
				 // Now use the session to create a text message
					TextMessage txtMsg = oSession.createTextMessage();
					//Serializable ChatMessageBean = new ChatMessageBean();
					
					//ObjectMessage oMsg = oSession.createObjectMessage();
				//  We retrieve the parameter 'message' from the request, and use it as text of our message
					txtMsg.setText(request.getParameter("message"));
					//ChatMessageBean chatMsg = (beans.ChatMessageBean) oMsg.getObject();
					
					long chatID = -1L;
					//Query to the DB the conversations of the users
					//SELECTS conv.ID FROM chatList WHERE conv.user == me
					//forEach conv.ID{IF conv.ID.contains(otherUser.Id) THEN break}
					if(chatID == -1L) {
						//new chatID, given by the DB has a unique identifier
						chatID = 1L;
					}
					int myID = 1312;					
					//String myID = "1312";
					
					/*
					//Fill the chatMessage object
					chatMsg.setDeliveryTime(System.currentTimeMillis());
					chatMsg.setSenderID(myID);
					chatMsg.setMsg(request.getParameter("message"));
					*/
					
					//Add the property chatID
					//oMsg.setLongProperty("chatID", chatID);
					txtMsg.setLongProperty("chatID", chatID);
					txtMsg.setLongProperty("deliveryTime", System.currentTimeMillis());
					
					txtMsg.setIntProperty("senderID", myID); //session.myID			

					//txtMsg.setStringProperty("senderID", myID);					
					
				// Use the message producer to send the message	
					oProducer.send(txtMsg);
				// Close the producer
				oProducer.close();
				// Close the session 
				oSession.close();
				// Close the connection 
				oConn.close();
				
				//Redirect
				config.getServletContext().getRequestDispatcher("/ReadMessageQueueBrowser.html").forward(request, response);
				
				//out.println(" Menssage sent </BR>");

			} catch (javax.jms.JMSException e) {
				System.out.println(
					"JHC *************************************** Error in doPost: "
						+ e);
				System.out.println(
					"JHC *************************************** Error MQ: "
						+ e.getLinkedException().getMessage());
				System.out.println(
					"JHC *************************************** Error MQ: "
						+ e.getLinkedException().toString());		
				System.out.println(" Error when sending the message</BR>");
		
				
			}catch (Exception e) {
				System.out.println(
					"JHC *************************************** Error in doPost: "
						+ e.toString());
				System.out.println(" Error when sending the message</BR>");
				
			}
			/*
			out.println(
			" >>>>>>  <A href=\"SendMessageToQueue.html\">Back</A></P>");
			out.println("</body></html>");
			*/
		}

				public void doGet(
					javax.servlet.http.HttpServletRequest request,
					javax.servlet.http.HttpServletResponse response)
					throws javax.servlet.ServletException, java.io.IOException {

					try {
						//Llamamos al m�todo doPost con los parametros que recibe este m�todo
						doPost(request, response);
					} catch (Exception e) {
						System.out.println(
							"JHC ***************************************Error in doGet: "
								+ e);
					}

				}

}
