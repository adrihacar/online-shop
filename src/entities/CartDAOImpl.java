package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

public class CartDAOImpl implements CartDAO{

	private EntityManager entityManager;
	
	private UserTransaction ut;
	
	public CartDAOImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
	}

	public CartDAOImpl(EntityManager entityManager, UserTransaction ut) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
		this.ut = ut;
	}

	
	@Override
	public void insert(CartBean cart) throws Exception {
		ut.begin();	
		entityManager.persist(cart);
		ut.commit();
	}

	@Override
	public void update(CartBean cart) throws Exception {
		ut.begin();
		entityManager.merge(cart);
		ut.commit();
	}

	@Override
	public CartBean findCartByUser(int user) {
		Query cartquery = entityManager.createNamedQuery("findCartByUser").setParameter("user",user);
		CartBean result = (CartBean) cartquery.getSingleResult();
		return result;
	}

	@Override
	public List<CartBean> findBoughtCartsByUser(int user) {
		Query cartsquery = entityManager.createNamedQuery("findBoughtCartsByUser").setParameter("user",user);
		List<CartBean> results = cartsquery.getResultList();
		return results;
	}

	@Override
	public CartBean findByID(int id) {
		CartBean cart = entityManager.find(CartBean.class, id);
		return cart;
	}

}
