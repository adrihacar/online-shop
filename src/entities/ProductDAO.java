package entities;

import java.util.List;

public interface ProductDAO {
	
	public void insert(ProductBean product) throws Exception;
	public void delte(ProductBean product) throws Exception;
	public void update(ProductBean product) throws Exception;
	public List<ProductBean> findAllProductsBySeller(int seller);
	public List<ProductBean> getAllProducts();
	public List<ProductBean> getProductsStatus(int status);
	public List<ProductBean> getProductsStatusBySeller(int custSeller, int custStatus);
	public ProductBean findByID(int id);
	public List<ProductBean> getProductByCategory(int category);

}
