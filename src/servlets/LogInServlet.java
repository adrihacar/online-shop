package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserBean;
import jdbc.UserDAOImp;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
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
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");
		
		
		UserDAOImp userDAOImp = new UserDAOImp();
		
		
		//check if client exist
		if(!userDAOImp.existClient(email)) {
			response.sendRedirect("/online_shop/login.jsp?status=error");
		}else {
			String salt = userDAOImp.getSaltFromDatabase(email);
			String hashedPassword = UserBean.getSHA256Hash(password, salt);
			if(userDAOImp.checkUser(email, hashedPassword)) {
				int id = userDAOImp.getIdFromEmail(email);
				//user created correctly, put in session
				HttpSession session = request.getSession(true);
		        session.setAttribute("user_id",id);
		        
		        response.sendRedirect("/online_shop/dashboard");
				
			}else {
				response.sendRedirect("/online_shop/login.jsp?status=error");
			}
		}
	}

}
