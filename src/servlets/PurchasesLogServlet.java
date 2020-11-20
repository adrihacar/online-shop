package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.CartBean;
import entities.CartDAOImpl;
import entities.PurchasedCart;
import entities.CartProductBean;
import entities.CartProductDAOImpl;
import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class PurchasesLogServlet
 */
@WebServlet({"/PurchasesLogServlet", "/purchases"})
public class PurchasesLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PURCHASES_JSP = "/purchasesLog.jsp";
	private static final String ERROR_JSP = "/errorPage.jsp";
	private ServletConfig config;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchasesLogServlet() {    	
        super();        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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
			System.out.println("UNEXPECTED ERROR in doGet: ");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		List<PurchasedCart> purchases = null;
		try {
			CartDAOImpl cartDAO = new CartDAOImpl("online_shop");
			CartProductDAOImpl cartProductDAO = new CartProductDAOImpl("online_shop");
			ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
			
			HttpSession oSession = request.getSession();
			int user = (int) oSession.getAttribute("user_id");
			
			
			// get the purchased carts of the logged user
			List<CartBean> boughtCarts = cartDAO.findBoughtCartsByUser(user);
			purchases = new ArrayList<PurchasedCart>();
			
			for (CartBean cart : boughtCarts) {
				// get id of the products in the cart
				List<CartProductBean> cartproducts = cartProductDAO.findProductsInCart(cart.getId()); //this can be sent to the jsp
				
				// retrieve products
				List<ProductBean> products = new ArrayList<>();
			    Iterator<CartProductBean> cartproductsIterator = cartproducts.iterator();
			    while(cartproductsIterator.hasNext()) {
			    	int productid = cartproductsIterator.next().getProduct();
			    	ProductBean product = productDAO.findByID(productid);
			    	products.add(product);
			    }			    
				purchases.add(new PurchasedCart(cart, products, cartproducts));						   
			}			
			//send needed attributes to the cart jsp
			request.setAttribute("purchases", purchases);
			config.getServletContext().getRequestDispatcher(PURCHASES_JSP).forward(request, response);
			
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in doPost: ");
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
			
		}
	}

}
