package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserCart;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CART_JSP = "/cart.jsp";
	private static final String ERROR_JSP = "/errorPage.jsp"; 
	private ServletConfig config;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();        
    }
    
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try {
			HttpSession session = request.getSession(true);
			Object userObject = session.getAttribute("user_id");
			
			if(userObject == null) {
				throw new Exception("There is no user logged in the session");				
			}
						
			UserCart currentUserCart = new UserCart((int) userObject);
			
			//Try to obtain from the Database the data of latest cart of the logged user 
			currentUserCart.updateToCurrentCart();
			
			//Set the needed attributes from the session
			session.setAttribute("userCart", currentUserCart);
			
			//Redirect
			config.getServletContext().getRequestDispatcher(CART_JSP).forward(request, response);
			
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR in CartServlet doGet: " + e);
			request.setAttribute("errorMsg", e.getMessage());
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			UserCart userCart = null;
			
			//Obtain the needed attributes from the request
			String action = request.getParameter("action");			
			// Obtain the needed attributes from the session
			HttpSession session = request.getSession(true);
						
			if (session.getAttribute("userCart") != null) {
				//The user cart is already loaded in the session. Retrieve it
				userCart = (UserCart) session.getAttribute("userCart");
			}else {
				//The userCart is not set yet. Create it
				Object userObject = session.getAttribute("user_id");
				if(userObject == null) {
					throw new Exception("There is no user logged in the session");
				}
				
				userCart = new UserCart((int) userObject);
				
				// get the current cart of the user				
				userCart.updateToCurrentCart();				
			}
						
			int productId;
			int quantity;
			
			if(action.equals("deleteProduct")){				
								
				productId = Integer.parseInt(request.getParameter("productRef"));
				userCart.deleteProduct(productId);
				
				session.setAttribute("userCart", userCart);			
				config.getServletContext().getRequestDispatcher(CART_JSP).forward(request, response);
				
			}else {
				// Receive product and quantity through parameters
				productId = Integer.parseInt(request.getParameter("product"));
				quantity = Integer.parseInt(request.getParameter("quantity"));
				
				if (action.equals("addToCart")) {					
					//Modify the product in the cart
					userCart.modifyProduct(productId, quantity, false);				
					response.sendRedirect("/online_shop/dashboard");
					
				}else if (action.equals("resetQuantity")) {					
					//Modify the product in the cart				
					userCart.modifyProduct(productId, quantity, true);					
					session.setAttribute("userCart", userCart);			
					config.getServletContext().getRequestDispatcher(CART_JSP).forward(request, response);
									
				}else {
					throw new Exception("Invalid action '" + action +"'");
				}
			}					

		} catch (Exception e) {
			e.printStackTrace(); 
			request.setAttribute("errorMsg", e.getMessage());			
			config.getServletContext().getRequestDispatcher(ERROR_JSP).forward(request, response);
		}
	}	
}
