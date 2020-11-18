package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.UserDAOImp;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserDAOImp userDAO = new UserDAOImp();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		HttpSession session = request.getSession(true);
		int currentUser = (int) session.getAttribute("user_id");
		
		userDAO.deleteUser(idUser);
				
		if(userDAO.isAdmin(currentUser)) {
			response.sendRedirect("/online_shop/AdminProductsServlet");
		} else {
			response.sendRedirect("/online_shop/dashboard");
		}
	}

}
