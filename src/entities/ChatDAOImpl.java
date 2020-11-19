package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jdbc.UserDAOImp;

public class ChatDAOImpl implements ChatDAO{
	
	private EntityManagerFactory emFact;
	
	public ChatDAOImpl(String persistenceUnit) {
		this.emFact = Persistence.createEntityManagerFactory(persistenceUnit);
	}

	/**
	 * Inserts a new chat in the 'chats' table
	 */
	@Override
	public void insert(ChatBean newChat) throws Exception {
		EntityManager em = emFact.createEntityManager();		
		em.getTransaction().begin();
		em.persist(newChat);
		em.getTransaction().commit();
		em.close();		
	}

	/**
	 * Updates a chat instance of the table 'chats'
	 */
	@Override
	public void update(ChatBean chat) throws Exception {
		EntityManager em = emFact.createEntityManager();		
		em.getTransaction().begin();
		em.merge(chat);
		em.getTransaction().commit();
		em.close();		
	}

	@Override
	public List<ChatBean> getChatsByUser(int user) {
		EntityManager em = emFact.createEntityManager();				
		Query chatQuery = em.createNamedQuery("findChatsByUser").setParameter("user", user);
		List<ChatBean> result = chatQuery.getResultList();
		em.close();
		return result;
	}

	@Override
	public ChatBean getChatById(long chatId) {
		EntityManager em = emFact.createEntityManager();		
		
		ChatBean chat = em.find(ChatBean.class, chatId);
		em.close();
		return chat;
	}
}
