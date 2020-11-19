package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserBean;
import jdbc.UserDAOImp;
import utils.CreateCart;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletConfig config;
    /**
     * @see HttpServlet#HttpServlet()
     */
	public EditUserServlet() {
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idUser;
		
		if(request.getParameter("idUser") != null) {
			idUser = Integer.parseInt(request.getParameter("idUser"));
		}else {
			HttpSession session = request.getSession();
			Object idObject = session.getAttribute("user_id");
			if(idObject == null) {
				request.setAttribute("errorMsg", "There is no user in the session!!");			
				RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
				rd.forward(request, response);
			}
			idUser = (int) idObject;
		}
		
		//TODO check if this user id  is the same as the id that the user is looged
		UserDAOImp userDAOImp = new UserDAOImp();
		
		UserBean user = userDAOImp.getUserdata(idUser);
		
		request.setAttribute("user", user);
		RequestDispatcher rd = request.getRequestDispatcher("/editUser.jsp");
		rd.forward(request, response);		
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
		String newPassword = request.getParameter("newPassword");
		String location = request.getParameter("Location");

		HttpSession session = request.getSession(true);
		
		UserDAOImp userDAOImp = new UserDAOImp();

		//success 
		UserBean user= new UserBean(name, surname, email, location, newPassword);
		Object idObject = session.getAttribute("user_id");
		if(idObject == null) {
			request.setAttribute("errorMsg", "There is no user in the session!!");			
			RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
			rd.forward(request, response);
		}
		int id = (int) idObject;

		userDAOImp.updateUser(user, id);

		response.sendRedirect("/online_shop/dashboard");


	}

}
