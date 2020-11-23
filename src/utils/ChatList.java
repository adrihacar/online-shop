package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import entities.ChatBean;
import entities.ChatDAOImpl;
import entities.UserChat;

public class ChatList<T> extends ArrayList<UserChat>{
	private static final long serialVersionUID = 1L;
	
	//CONSTANTS
	private static final String PERSISTENCE_UNIT = "online_shop";
	
	//ATTRIBUTES
	private UserChat activeChat;
	//Id of the user owner of this UserChats
	private int owner;	
			
			
	//CONSTRUCTORS
	public ChatList() { }
	
	public ChatList(int user) {		
		this.owner = user;
	}
	
	//getters & setters
	public void getChatList() {
		
	}
	
	public UserChat getActiveChat() {
		return activeChat;
	}

	public void setActiveChat(UserChat activeChat) {
		this.activeChat = activeChat;
	}
	
	//AUXILIARY METHODS
	/**
	 * Populates this list with the chats belonging to its owner (ChatList.owner)
	 * @throws Exception When the Connection with the DataSource fails
	 */
	public void retrieveChatsInfo() throws Exception {
	  			
		ChatDAOImpl chatDAO = new ChatDAOImpl(PERSISTENCE_UNIT);		
		parseChatList((List<ChatBean>) chatDAO.getChatsByUser(this.owner));				
	}
	
	/**
	 * Selects an UserChat from this ChatList using the chatId  
	 * @param chat Id of the UserChat to be found
	 * @return The requested UserChat if found. Null otherwise.
	 */
	public UserChat getChatById(long chat) {
		UserChat foundChat = null; 
		Iterator<UserChat> it = this.iterator();
		while(it.hasNext()) {
			UserChat userChat = it.next();						
			if(userChat.getChatId() == chat) {
				foundChat = userChat;
			}
		}
		return foundChat;
	}
	
	/**
	 * Swaps the active chat of this ChatList
	 * @param recipient User recipient of the messages
	 * @throws Exception When trying to use an invalid id for the recipient
	 */
	public void swapActiveChat(int recipient) throws Exception {
			if(recipient > 0) {
								
				this.activeChat = findChatWithUser(recipient);
				
				if(this.activeChat == null) {
					System.out.println("********** CREATING NEW LOCAL CHAT");
					//chat not found. The logged user is starting a new chat
					//Create a new chat (locally) and add it to the local chatList (NOT COMMITED TO THE DB YET)						
					UserChat oNewChat = UserChat.newEmptyChat(this.owner, recipient);
					System.out.println("************ NEW CHAT ID = " + oNewChat.getChatId());
					this.add(0, oNewChat);
					this.activeChat = oNewChat;					
				}
			}else{
				throw new Exception("UserId cannot be negative");
			}			
		
	}
	
	/**
	 * Creates a list of ChatElement objects based on a collection of ChatBeans
	 * @param userChatBeans List object containing the ChatBeans
	 * @return a List object containing the chatElements created from the input. Null if there was any error
	 * @throws Exception 
	 */
	private void parseChatList(List<ChatBean> userChatBeans) throws Exception {
				
		if(!userChatBeans.isEmpty()) {
			try {				
				//For each ChatBean, create its corresponding UserChat object
				for (ChatBean chat : userChatBeans) {
					UserChat userChat = new UserChat(this.owner);
					//Obtain all the relevant chat info from the ChatBean
					userChat.parseUserChat(chat);
					
					this.add(userChat);

				}//End of loop

				//By default the active chat is the first in the list, the most recent
				this.activeChat = this.get(0);
				
				System.out.println("****************** [ChatList] New active chat --> " + this.activeChat.getRecipientUserName());
				
			} catch (Exception e) {
				System.out.println("ERROR retrieving the chats from the database");
				e.printStackTrace();			
				throw e;
			}		

		}		
	}
	
	/**
	 * Checks if exist a chat between the owner of this chats and the user specified.
	 * if the chat exist within this list, then returns the corresponding UserChat
	 * 
	 * @param user User recipient of the messages
	 * @return the UserChat between the logged user and the specified user. 
	 * Null if no coincidence was found
	 */
	public UserChat findChatWithUser(int user) {
		UserChat foundUserChat = null;
		
		for (int i = 0; i < this.size(); i++) {
			System.out.println("*********** find next chat");
			UserChat chat = this.get(i);
			if(chat.isBetween(this.owner, user)) {
				foundUserChat = chat;
			}
		}			
		return foundUserChat;

	}
/**
 * Empties all the message history of all the chats of this list
 * The goal of this method is to reduce the size of the object keeping only the minimum information 
 */
	public void purge() {
		for (int i = 0; i < this.size(); i++) {
			this.get(i).deleteMsgHistory();
		}
		
	}
	
	


}
