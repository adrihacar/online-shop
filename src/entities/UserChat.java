package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.jms.TextMessage;

import jdbc.UserDAOImp;

public class UserChat extends ChatBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//CONSTANTS
	public static final String CHAT_TO_BUY = "toBuy";
	public static final String CHAT_TO_SELL = "toSell";		
	
	//FIELDS
	private int owner;
	private String recipientUserName;
	private String lastMsgText;
	private long lastMsgDate;	
	/**
	 * Indicates whether the chat type is 
	 * 'toBuy' (started by the logged user)
	 * or 'toSell' (not started by the logged user)  
	 */
	private String chatType;
	private List<ChatMessage> messages;
	
	
	//CONSTRUCTORS
	public UserChat() {};

	public UserChat(long chatID){
		this.setChatId(chatID);
	}
	
	public UserChat(int owner) {
		this.owner = owner;
	}
	
	//STATIC METHODS
	/**
	 * Creates a new Chat with no messages, not linked with any ChatBean
	 * @param owner Id of the user creating this chat
	 * @param recipient Id of the other user involved in the conversation
	 * @return A new UserChat object that with no messages
	 */
	public static UserChat newEmptyChat(int owner, int recipient){
		UserChat newChat = new UserChat();
		
		newChat.setChatId(-1L);
		newChat.setBuyer(owner);
		newChat.setSeller(recipient);
		newChat.setLastMsgId("");				
		
		UserDAOImp userDAO = new UserDAOImp();
		UserBean userInfo = userDAO.getUserdata(recipient);
		
		newChat.chatType = CHAT_TO_BUY;
		newChat.recipientUserName = userInfo.getName() + " " +userInfo.getSurname();
		newChat.lastMsgText="";
		newChat.lastMsgDate = 0L;
		newChat.messages = new ArrayList<ChatMessage>();
		
		return newChat;
	}
	
	//GETTERS & SETTERS
	public String getRecipientUserName() {
		return recipientUserName;
	}
	public void setRecipientUserName(String recipientUser) {
		this.recipientUserName = recipientUser;
	}
	public String getLastMsgText() {		
		return lastMsgText;
	}
	public void setLastMsgText(String lastMsg) {
		this.lastMsgText = lastMsg;
	}
	public long getLastMsgDate() {
		return lastMsgDate;
	}
	
	public String getLastMsgDateToString(Locale locale) {
		String msgDate = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.lastMsgDate);
		
		//Obtain the short String representation of the month of the given calendar
		String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, locale);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		msgDate += day + "   " + month;

		return msgDate;
	}
	public void setLastMsgDate(long lastMsgDate) {
		this.lastMsgDate = lastMsgDate;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}
	
	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}
	/**
	 * 
	 * @return The id of the user recipient of the messages of this Chat
	 */
	public int getRecipientUserId() {
		int recipient = 0;
		if (this.owner == this.getBuyer()) {
			recipient = this.getSeller();
		}else {
			recipient = this.getBuyer();
		}
		return recipient;
		
	}
	
	//AUXILIARY METHODS
	public void parseUserChat(ChatBean chatBean) {
		
		this.updateChatInfo(chatBean);
		
		UserDAOImp userDAO = new UserDAOImp();
		
		//Get the data from the other user participating in the chat, that is not the owner of this UserChat
		UserBean user;
		if(chatBean.getBuyer() == this.owner) {
			user = userDAO.getUserdata(chatBean.getSeller()); 			
			this.setChatType(UserChat.CHAT_TO_BUY);
		}else {
			user = userDAO.getUserdata(chatBean.getBuyer());  			
			this.setChatType(UserChat.CHAT_TO_SELL);
		}					

		//Set the values of the UserChat
		this.setRecipientUserName(user.getName() + " " + user.getSurname());									
		this.lastMsgText = "";
		this.lastMsgDate = 0L;
		this.messages = new ArrayList<ChatMessage>();
				
	}
	
	/**
	 * Updates the attributes inherited from ChatBean to the new values specified in another ChatBean
	 * @param chatBean Object ChatBean containing the new information
	 */
	public void updateChatInfo(ChatBean chatBean) {						
		this.setChatId(chatBean.getChatId());
		this.setBuyer(chatBean.getBuyer());
		this.setSeller(chatBean.getSeller());
		this.setLastMsgId(chatBean.getLastMsgId());			

	}
			
	/**
	 * Updates the last message attributes according to a new message
	 * @param lastMsg TextMessage with the new information 
	 * @throws Exception
	 */
	public void updateLastMsg(TextMessage lastMsg) throws Exception {				
		this.setLastMsgText(lastMsg.getText());
		this.setLastMsgDate(lastMsg.getJMSTimestamp());		
	}
	
	/**
	 * Inserts into the list of messages the ChatMessages retrieved from the server message queue
	 * @param msgHistory List of ChatMessages objects
	 */
	public void loadMessages(List<ChatMessage> msgHistory) {
		this.messages.addAll(msgHistory);
	}
		
	/**
	 * Checks if the calling chat has the specified users as participants (buyer and seller)
	 * 
	 * @param user1 ID of a user having a conversation
	 * @param user2 ID of a user having a conversation
	 * @return True if the chat is between the two users specified in the parameters
	 */
	public boolean isBetween(int user1, int user2) {
		boolean from1to2 = (this.getBuyer() == user1 && this.getSeller() == user2);
		boolean from2to1 = (this.getBuyer() == user2 && this.getSeller() == user1); 
		return (from1to2 || from2to1);			
	}

/**
 * 
 * @return The HTML code generated for the message history of this chat
 */
	public String printMsgHistoryHtml(){
		String msgHistoryHtml = "";
		for (int i = 0; i < this.messages.size(); i++){
			boolean isLastMsg = false;
			ChatMessage msg = this.messages.get(i);

			if(i == this.messages.size() - 1){	isLastMsg = true; }
						
			msgHistoryHtml += msg.toHtml(this.owner, isLastMsg);
			
		}
		return msgHistoryHtml;
	}

/**
 * Resets the message history of the chat, setting the message list to a new empty list	
 */
public void deleteMsgHistory() {
	this.messages = new ArrayList<ChatMessage>();
	
}

	

	
	
	
}
