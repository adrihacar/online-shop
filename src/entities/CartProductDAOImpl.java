package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

public class CartProductDAOImpl implements CartProductDAO{

	
	private EntityManager entityManager;
	
	private UserTransaction ut;
	
	public CartProductDAOImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
	}

	public CartProductDAOImpl(EntityManager entityManager, UserTransaction ut) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shop");
		this.entityManager = emf.createEntityManager();
		this.ut = ut;
	}
	
	
	@Override
	public void insert(CartProductBean cartproduct) throws Exception {
		ut.begin();	
		entityManager.persist(cartproduct);
		ut.commit();
	}

	@Override
	public void delete(CartProductBean cartproduct) throws Exception {
		ut.begin();
		if (!entityManager.contains(cartproduct)) {
			cartproduct = entityManager.merge(cartproduct);
		}
		entityManager.remove(cartproduct);
		ut.commit();
	}

	@Override
	public void update(CartProductBean cartproduct) throws Exception {
		ut.begin();
		entityManager.merge(cartproduct);
		ut.commit();
	}

	@Override
	public List<CartProductBean> findProductsInCart(int cart) {
		Query cartproductsquery = entityManager.createNamedQuery("findProductsInCart").setParameter("cart",cart);
		List<CartProductBean> results = cartproductsquery.getResultList();
		return results;
	}

	@Override
	public CartProductBean findByID(int id) {
		CartProductBean cartproduct = entityManager.find(CartProductBean.class, id);
		return cartproduct;
	}

}
