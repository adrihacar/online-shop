package utils;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class PaymentGatewayConsumer implements MessageListener{
	@Resource(mappedName = "PaymentGatewayFactory") //logic name
    private ConnectionFactory connectionfactory;

    @Resource(mappedName = "PaymentGateway") //logic name
    private Queue queue;
    
    public PaymentGatewayConsumer() throws JMSException {
        super();
        // Create a connection using the connectionFactory
        connection = connectionfactory.createConnection();
        // Next create the session. Indicate that transaction will not be supported
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    Connection connection;
    Session session;
    
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}
    
	@Override
	public void onMessage(Message msg){
		MessageConsumer consumer;
		try {
			consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);
			connection.start();
			long cart = msg.getLongProperty("cart");
			long paymethod = msg.getLongProperty("paymethod");
			String address = msg.getStringProperty("address");
			long date = msg.getLongProperty("date");
			System.out.println(cart);
			System.out.println(paymethod);
			System.out.println(address);
			System.out.println(date);
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}

}
