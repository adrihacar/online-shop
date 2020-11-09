package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.ProductBean;

public class ProductDAOImpl implements ProductDAO {
	
	private EntityManagerFactory emf;
	
	public ProductDAOImpl(String unidadDePersistencia)
	{
		this.emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	@Override
	public void insert(ProductBean product) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void delte(ProductBean product) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		if (!entityManager.contains(product)) {
			product = entityManager.merge(product);
		}
		entityManager.remove(product);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void update(ProductBean product) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.merge(product);
		entityManager.getTransaction().commit();
		entityManager.close();		
	}

	@Override
	public List<ProductBean> findAllProductsBySeller(int seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBean> getAllProducts() {
		EntityManager entityManager = emf.createEntityManager();
		Query products = entityManager.createNamedQuery("getAllProducts");
		List<ProductBean> results = products.getResultList();
		return results;
	}

	@Override
	public List getProductsStatus(int status) {
		EntityManager entityManager = emf.createEntityManager();
		Query products = entityManager.createNamedQuery("getProductsStatus").setParameter("custStatus",status);
		List results = products.getResultList();
		return results;
	}

	@Override
	public List<ProductBean> getProductsStatusBySeller(int custSeller, int custStatus) {
		EntityManager entityManager = emf.createEntityManager();
		Query productsQuery = entityManager.createNamedQuery("getProductsStatusBySeller").setParameter("custSeller",custSeller).setParameter("custStatus", custStatus);
		List<ProductBean> products = productsQuery.getResultList();
		return products;
	}

	@Override
	public ProductBean findByID(int id) {
		EntityManager entityManager = emf.createEntityManager();
		ProductBean product = entityManager.find(ProductBean.class, id);
		return product;
	}

	@Override
	public List<ProductBean> getProductByCategory(int category) {
		EntityManager entityManager = emf.createEntityManager();
		Query productsQuery = entityManager.createNamedQuery("getProductsCategory").setParameter("custCategory",category);
		List<ProductBean> products = productsQuery.getResultList();
		return products;
	} 

}
