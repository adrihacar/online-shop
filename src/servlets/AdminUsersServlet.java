package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserBean;
import jdbc.UserDAOImp;

/**
 * Servlet implementation class AdminUsersServlet
 */
@WebServlet("/AdminUsersServlet")
public class AdminUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserDAOImp userDAO = new UserDAOImp();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserBean> users = userDAO.getAllUsers();
		request.setAttribute("users", users);
		
		RequestDispatcher rd = request.getRequestDispatcher("/adminUserList.jsp");
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
