package servlets;

import java.io.IOException;
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

import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class CatalogServlet
 */
@WebServlet("/Catalog")
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductBean productBean = new ProductBean();
	
	ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogServlet() {
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
		List results;
		
		if(request.getParameter("sold") == null || request.getParameter("sold").equals("false")){
			results = productDAO.getProductsStatusBySeller(1, 0);
			request.setAttribute("sold", "false");
		} else {
			results = productDAO.getProductsStatusBySeller(1, 1);
			request.setAttribute("sold", "true");
		}
		request.setAttribute("products", results);
		
		RequestDispatcher rd = request.getRequestDispatcher("/catalog.jsp");
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
