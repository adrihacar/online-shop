package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;

public class CartDAOImpl implements CartDAO{

	private EntityManagerFactory emf;
	
	public CartDAOImpl(String unidadDePersistencia)
	{
		this.emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	  EntityManagerFactory emf = this.emf;
	  emf.close();
	}
	
	@Override
	public void insert(CartBean cart) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.persist(cart);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void update(CartBean cart) throws Exception {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();	
		entityManager.merge(cart);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public CartBean findCartByUser(int user) {
		EntityManager entityManager = emf.createEntityManager();
		Query cartquery = entityManager.createNamedQuery("findCartByUser").setParameter("user",user);
		CartBean result = (CartBean) cartquery.getSingleResult();
		entityManager.close();
		return result;
	}

	@Override
	public List<CartBean> findBoughtCartsByUser(int user) {
		EntityManager entityManager = emf.createEntityManager();
		Query cartsquery = entityManager.createNamedQuery("findBoughtCartsByUser").setParameter("user",user);
		List<CartBean> results = cartsquery.getResultList();
		entityManager.close();
		return results;
	}

	@Override
	public CartBean findByID(int id) {
		EntityManager entityManager = emf.createEntityManager();
		CartBean cart = entityManager.find(CartBean.class, id);
		entityManager.close();
		return cart;
	}

}
