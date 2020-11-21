package entities;

import java.util.List;

import javax.servlet.ServletContextEvent;

public interface CartProductDAO {

	public void insert(CartProductBean cartproduct) throws Exception;
	public void delete(CartProductBean cartproduct) throws Exception;
	public void update(CartProductBean cartproduct) throws Exception;
	public List<CartProductBean> findProductsInCart(int cart);
	public CartProductBean findByID(int id);
	void contextDestroyed(ServletContextEvent sce);
	
}
