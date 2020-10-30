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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import entities.ProductBean;

/**
 * Servlet implementation class editProductServlet
 */
@WebServlet("/editProduct")
public class editProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	
	@PersistenceContext(unitName="online_shop")
	private EntityManager entityManager;
	
	@Resource
	UserTransaction ut;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
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
		
		ProductBean product = entityManager.find(ProductBean.class, 1);
		
		String productName = request.getParameter("productName");
		String cattegoryProduct = request.getParameter("cattegoryProduct");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		
		
		HttpSession session = request.getSession(true);
		Object objectUser = session.getAttribute("user");
		if(objectUser == null) {
			//ERROR
		}
		else {
			product.setSeller(((Integer) objectUser).intValue());
		}
		
		product.setName(productName);
		product.setPrice(Integer.parseInt(price));
		product.setCategory(Integer.parseInt(cattegoryProduct));
		product.setDescription(description);
		product.setImage("");
		
		try {
			ut.begin();
		
			entityManager.persist(product);
			
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		config.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);

	}

}
