package servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class DeleteProductSrvlet
 */
@WebServlet({ "/DeleteProductSrvlet", "/deleteProduct" })
public class DeleteProductSrvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	
	ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProductSrvlet() {
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductBean product = productDAO.findByID(Integer.parseInt(request.getParameter("idProduct")));
		try {
			productDAO.delte(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/online_shop/Catalog");
		
	}

}
