package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserBean;
import jdbc.UserDAOImp;
import utils.CreateCart;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public RegisterServlet() {
		
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
		// TODO Auto-generated method stub
		String name = request.getParameter("Name");
		String surname = request.getParameter("Surname");
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");
		String location = request.getParameter("Location");
		
		
		UserDAOImp userDAOImp = new UserDAOImp();
		
		//check if the client exists, checking email
		if(userDAOImp.existClient(email)) {
			response.sendRedirect("/online_shop/register.jsp?status=email_exist");
			response.getWriter().append("Error: User exists").append(request.getContextPath());
			
		}else {
			//success 
			UserBean user= new UserBean(name, surname, email, location, password);
			userDAOImp.insertUser(user);
			
			int id = userDAOImp.getIdFromEmail(email);
			//create empty cart
	        CreateCart createCart = new CreateCart();
	        createCart.createCart(id);
	        
	        response.sendRedirect("/online_shop/login.jsp");
		}

	}

}
