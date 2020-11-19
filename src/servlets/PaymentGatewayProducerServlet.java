package servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class PaymentGatewayProducer
 */
@WebServlet("/payments")
public class PaymentGatewayProducerServlet extends HttpServlet {
	
	@Resource(mappedName = "PaymentGatewayFactory") //logic name
    private ConnectionFactory tiwconnectionfactory;

    @Resource(mappedName = "PaymentGateway") //logic name
    private Queue queue;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentGatewayProducerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long cart = Long.parseLong(request.getParameter("cart"));
		long paymethod = Long.parseLong(request.getParameter("paymethod"));
		String address = request.getParameter("address");
		long date = System.currentTimeMillis() / 1000L; // get payment date in unix time
		
		// Create a connection using the connectionFactory
        Connection oConn;
		try {
			oConn = tiwconnectionfactory.createConnection();
	        // Next create the session. Indicate that transaction will not be supported
	        Session oSession = oConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        // Create producer
	        MessageProducer oProducer = oSession.createProducer(queue);
			
			//Create the message
	        TextMessage txtMsg = oSession.createTextMessage();

	        //Retrieve the parameter 'message' from the request, and use it as text of our message
	        txtMsg.setText("");

	        //Set additional properties of the message
	        txtMsg.setLongProperty("cart", cart);
	        txtMsg.setLongProperty("paymethod", paymethod);
	        txtMsg.setStringProperty("address", address);
	        txtMsg.setLongProperty("date", date);

	        // Use the message producer to send the message
	        oProducer.send(txtMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/purchased.jsp");
		rd.forward(request, response);
	
	}

}
