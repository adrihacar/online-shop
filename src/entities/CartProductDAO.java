package entities;

import java.util.List;

public interface CartProductDAO {

	public void insert(CartProductBean cartproduct) throws Exception;
	public void delete(CartProductBean cartproduct) throws Exception;
	public void update(CartProductBean cartproduct) throws Exception;
	public List<CartProductBean> findProductsInCart(int cart);
	public CartProductBean findByID(int id);
	
}
