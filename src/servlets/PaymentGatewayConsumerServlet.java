package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.CartBean;
import entities.CartDAOImpl;
import entities.CartProductBean;
import entities.CartProductDAOImpl;
import entities.ProductBean;
import entities.ProductDAOImpl;
import utils.CreateCart;

/**
 * Servlet implementation class PaymentGatewayConsumerServlet
 */
@WebServlet("/consumepayment")
public class PaymentGatewayConsumerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(mappedName = "PaymentGatewayFactory") //logic name
    private ConnectionFactory connectionfactory;

    @Resource(mappedName = "PaymentGateway") //logic name
    private Queue queue;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentGatewayConsumerServlet() {
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
		CartDAOImpl cartDAO = new CartDAOImpl("online_shop");
		CartProductDAOImpl cartProductDAO = new CartProductDAOImpl("online_shop");
		ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
		MessageConsumer consumer;
		try {
            // Create a connection using the connectionFactory
            Connection connection = connectionfactory.createConnection();
            // Next create the session. Indicate that transaction will not be supported
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(queue);
			connection.start();
			// receive the message
			Message msg = consumer.receive(500);
			int cart = msg.getIntProperty("cart");
			String paymethod = msg.getStringProperty("paymethod");
			String address = msg.getStringProperty("address");
			long date = msg.getLongProperty("date");
			
			
			// get cart
			CartBean cartBean = cartDAO.findByID(cart);

			// get all products and set them to sold
			List<CartProductBean> cartproducts = cartProductDAO.findProductsInCart(cart);
		    Iterator<CartProductBean> cartproductsIterator = cartproducts.iterator();
		    while(cartproductsIterator.hasNext()) {
		    	int productid = cartproductsIterator.next().getProduct();
		    	ProductBean product = productDAO.findByID(productid);
		    	product.setStatus(1);
		    	productDAO.update(product);
		    }
		    // update cart
			cartBean.setAddress(address);
			cartBean.setBought(true);
			cartBean.setDate(date);
		    cartDAO.update(cartBean);
		    // create new empty cart
	        CreateCart createCart = new CreateCart();
	        createCart.createCart(cartBean.getUser());
			connection.close();
			consumer.close();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/purchased.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		
	}

}
