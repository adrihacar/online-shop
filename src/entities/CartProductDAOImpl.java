package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.transaction.UserTransaction;

public class CartProductDAOImpl implements CartProductDAO{

	
	private EntityManagerFactory emf;
	
	public CartProductDAOImpl(String unidadDePersistencia)
	{
		this.emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	  EntityManagerFactory emf = this.emf;
	  emf.close();
	}
	
	@Override
	public void insert(CartProductBean cartproduct) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.persist(cartproduct);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void delete(CartProductBean cartproduct) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		if (!entityManager.contains(cartproduct)) {
			cartproduct = entityManager.merge(cartproduct);
		}
		entityManager.remove(cartproduct);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void update(CartProductBean cartproduct) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.merge(cartproduct);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<CartProductBean> findProductsInCart(int cart) {
		EntityManager entityManager = emf.createEntityManager();
		Query cartproductsquery = entityManager.createNamedQuery("findProductsInCart").setParameter("cart",cart);
		List<CartProductBean> results = cartproductsquery.getResultList();
		return results;
	}

	@Override
	public CartProductBean findByID(int id) {
		EntityManager entityManager = emf.createEntityManager();
		CartProductBean cartproduct = entityManager.find(CartProductBean.class, id);
		return cartproduct;
	}

}
