package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.CartBean;
import entities.CartDAOImpl;
import entities.CartProductBean;
import entities.CartProductDAOImpl;
import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
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
		
		// TODO get user id
		HttpSession session = request.getSession(true);
        int user = (int) session.getAttribute("user_id");
		
		// get cart of user
		CartBean cart = cartDAO.findCartByUser(user);
		
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
		
	    //send needed attributes to the cart jsp
		request.setAttribute("products", products); // to get the products in a cart
		request.setAttribute("cartproducts", cartproducts); // to know
		RequestDispatcher rd = request.getRequestDispatcher("/cart.jsp");
		rd.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
