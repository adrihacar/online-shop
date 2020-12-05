package servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import entities.UserCart;

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
		
		List<UserCart> purchases = null;
		try {
			CartDAOImpl cartDAO = new CartDAOImpl("online_shop");			
			
			HttpSession oSession = request.getSession();
			Object userObject = oSession.getAttribute("user_id");
			
			if(userObject == null) {
				throw new Exception("There is no user logged in the session");				
			}
			int user = (int) userObject;
						
			// get the purchased carts of the logged user
			List<CartBean> boughtCarts = cartDAO.findBoughtCartsByUser(user);
			purchases = new ArrayList<UserCart>();
			
			for (CartBean cart : boughtCarts) {
				UserCart boughtCart = new UserCart(user);				
				boughtCart.parseUserCart(cart);
				purchases.add(boughtCart);				
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
