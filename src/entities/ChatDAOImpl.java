package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ChatDAOImpl implements ChatDAO{
	
	private EntityManagerFactory emFact;
	
	public ChatDAOImpl(String persistenceUnit) {
		this.emFact = Persistence.createEntityManagerFactory(persistenceUnit);
	}

	/**
	 * Inserts a new chat in the 'chats' table
	 * @param newChat new chat to insert in the database
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
	 * @param chat Chat to be updated in the database
	 */
	@Override
	public void update(ChatBean chat) throws Exception {
		EntityManager em = emFact.createEntityManager();		
		em.getTransaction().begin();
		em.merge(chat);
		em.getTransaction().commit();
		em.close();		
	}

	/**
	 * Query the database for chats involving the specified user.
	 * @param user Id of the user requesting the chats
	 * @return A list of ChatBeans that belong to the user
	 */
	@Override
	public List<ChatBean> getChatsByUser(int user) {
		EntityManager em = emFact.createEntityManager();				
		Query chatQuery = em.createNamedQuery("findChatsByUser").setParameter("user", user);
		List<ChatBean> result = chatQuery.getResultList();
		em.close();
		return result;
	}

	/**
	 * Query the database to obtain the chat with the specified id
	 * @param chatId Id of the chat to be retrieved from the database
	 * @return The specified ChatBean. Null if the chat was not found in the database 
	 */
	@Override
	public ChatBean getChatById(long chatId) {
		EntityManager em = emFact.createEntityManager();		
		
		ChatBean chat = em.find(ChatBean.class, chatId);
		em.close();
		return chat;
	}
}
