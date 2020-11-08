package servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

import entities.ProductBean;

/**
 * Servlet implementation class editProductServlet
 */
@WebServlet("/editProduct")
@MultipartConfig
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
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductBean product = entityManager.find(ProductBean.class, Integer.parseInt(request.getParameter("idProduct")));
		request.setAttribute("product", product);
		
		RequestDispatcher rd = request.getRequestDispatcher("/editProduct.jsp");
		rd.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductBean product = entityManager.find(ProductBean.class, Integer.parseInt(request.getParameter("idProduct")));
		
		String productName = request.getParameter("productName");
		int cattegoryProduct = Integer.parseInt(request.getParameter("cattegoryProduct"));
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		Part filePart = request.getPart("image");
		
		
		HttpSession session = request.getSession(true);
		Object objectUser = session.getAttribute("user");
		if(objectUser == null) {
			//ERROR
		}
		else {
			//get seller
		}
		product.setSeller(1);
		product.setId(Integer.parseInt(request.getParameter("idProduct")));
		product.setName(productName);
		product.setPrice(price);
		product.setCategory(cattegoryProduct);
		product.setDescription(description);
		byte[] data = new byte[(int) filePart.getSize()];
	    filePart.getInputStream().read(data, 0, data.length);
	    product.setImage(data);
		
		try {
			ut.begin();
		
			entityManager.merge(product);
			//entityManager.createNamedQuery("updateProduct").setParameter("newName", productName).setParameter("newDescription", description).setParameter("newCategory", cattegoryProduct).setParameter("newPrice", price).setParameter("custId", Integer.parseInt(request.getParameter("idProduct")));
			
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/online_shop/dashboard");
		
		//RequestDispatcher rd = request.getRequestDispatcher("/dashboard");
		//rd.forward(request, response);
		
	}

}
