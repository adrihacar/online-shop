package entities;

import java.util.Calendar;
import java.util.Locale;

public class ChatElement extends ChatBean {
	//CONSTANTS
	public static final String CHAT_TO_BUY = "toBuy";
	public static final String CHAT_TO_SELL = "toSell";
	
	//FIELDS
	private String recipientUser;
	private String lastMsgText;
	private long lastMsgDate;
	/**
	 * Indicates whether the chat type is 
	 * 'toBuy' (started by the logged user)
	 * or 'toSell' (not started by the logged user)  
	 */
	private String chatType;
	
	//CONSTRUCTORS
	public ChatElement() {};
	
	public ChatElement(String recipientUser, String lastMsg, long lastMsgDate, String chatType) {
		super();
		this.recipientUser = recipientUser;
		this.lastMsgText = lastMsg;
		this.lastMsgDate = lastMsgDate;
		this.chatType = chatType;
	}
	
	public ChatElement(ChatBean chatBean) {
		new ChatElement();
		this.setChatID(chatBean.getChatID());
		this.setBuyer(chatBean.getBuyer());
		this.setSeller(chatBean.getSeller());
		this.setLastMsgId(chatBean.getLastMsgId());
	}
	
	//GETTERS & SETTERS
	public String getRecipientUser() {
		return recipientUser;
	}
	public void setRecipientUser(String recipientUser) {
		this.recipientUser = recipientUser;
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
		chatType = chatType;
	}
	
	
	
}
