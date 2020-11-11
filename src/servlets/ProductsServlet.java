package servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class ProductsServlet
 */
@WebServlet({ "/dashboard" })
@MultipartConfig
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductBean productBean = new ProductBean();
	
	ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest reTODO Auto-generated method stubquest, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProductBean> results = productDAO.getProductsStatus(0);
		request.setAttribute("products", results);
		
		RequestDispatcher rd = request.getRequestDispatcher("/dashboard.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
	}

}
