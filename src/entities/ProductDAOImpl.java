package entities;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.Path;

import entities.ProductBean;

public class ProductDAOImpl implements ProductDAO {
	
	private EntityManager entityManager;
	
	private UserTransaction ut;
	
	public ProductDAOImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
	}

	public ProductDAOImpl(EntityManager entityManager, UserTransaction ut) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
		this.ut = ut;
	}

	@Override
	public void insert(ProductBean product) throws Exception {
		ut.begin();	
		entityManager.persist(product);
		ut.commit();
	}

	@Override
	public void delte(ProductBean product) throws Exception {
		ut.begin();
		if (!entityManager.contains(product)) {
			product = entityManager.merge(product);
		}
		entityManager.remove(product);
		ut.commit();
	}

	@Override
	public void update(ProductBean product) throws Exception {
		ut.begin();
		entityManager.merge(product);
		ut.commit();
		
	}

	@Override
	public List<ProductBean> findAllProductsBySeller(int seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBean> getAllProducts() {
		Query products = entityManager.createNamedQuery("getAllProducts");
		List<ProductBean> results = products.getResultList();
		return results;
	}

	@Override
	public List getProductsStatus(int status) {
		Query products = entityManager.createNamedQuery("getProductsStatus").setParameter("custStatus",status);
		List results = products.getResultList();
		return results;
	}

	@Override
	public List<ProductBean> getProductsStatusBySeller(int custSeller, int custStatus) {
		Query productsQuery = entityManager.createNamedQuery("getProductsStatusBySeller").setParameter("custSeller",custSeller).setParameter("custStatus", custStatus);
		List<ProductBean> products = productsQuery.getResultList();
		return products;
	}

	@Override
	public ProductBean findByID(int id) {
		ProductBean product = entityManager.find(ProductBean.class, id);
		return product;
	} 

}
