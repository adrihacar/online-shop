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

import entities.ProductBean;
import entities.ProductDAOImpl;

/**
 * Servlet implementation class AdminProductsServlet
 */
@WebServlet("/AdminProductsServlet")
public class AdminProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProductsServlet() {
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
		List<ProductBean> products = productDAO.getAllProducts();
		
		request.setAttribute("products", products);
		
		RequestDispatcher rd = request.getRequestDispatcher("/adminProductList.jsp");
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
