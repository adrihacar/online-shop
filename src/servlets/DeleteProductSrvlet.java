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

/**
 * Servlet implementation class DeleteProductSrvlet
 */
@WebServlet({ "/DeleteProductSrvlet", "/deleteProduct" })
public class DeleteProductSrvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	
	@PersistenceContext(unitName="online_shop")
	private EntityManager entityManager;
	
	@Resource
	UserTransaction ut;
    
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
		
		ProductBean product = entityManager.find(ProductBean.class, Integer.parseInt(request.getParameter("idProduct")));
		
		try {
			ut.begin();
		
			if (!entityManager.contains(product)) {
				product = entityManager.merge(product);
			}
			entityManager.remove(product);
			
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/online_shop/Catalog");
		
	}

}
