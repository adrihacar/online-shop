package beans;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NamedQueries({
	@NamedQuery(name="findChatsByUser",
			query="SELECT c FROM ChatBean c WHERE (c.buyer =:user OR c.seller =: user) ORDER BY c.lastMsgId DESC"),
	@NamedQuery(name ="updateLastMsg",
			query="UPDATE ChatBean c SET c.lastMsgId = :latestMsgId")
})
@Table(name="chats")
public class ChatBean {
	/** Id of the chat*/
	//All the relationships of this Entity are unidirectional
	
	@Id
	@GeneratedValue(strategy = AUTO)
	private long chatID;
	
	@Column(name="buyer")
	@NotNull
	private int buyer;
	
	@Column(name="seller")
	@NotNull
	private int seller;
	
	@Column(name="lastMsgId")
	//May be null (if the chat has been created but no messages have been sent)
	private String lastMsgId;
	

	
	/** Empty constructor. Used by JPA */
	public ChatBean() {}
	
	/** Complete constructor */
	public ChatBean(long chatID, int buyer, int seller, String lastMsgId) {
		super();
		this.chatID = chatID;
		/* Table chatList = chatID | buyer | seller 
		 * buyer is the active user, starting the conversation (wants to buy a product)
		 * seller is the passive user (has a product to sell)
		 * In this way the passive users can quickly report those users that are using the chat to spam advertising 
		 * 
		 * This field is the result of 
		 *  
		 * 	SELECT chat FROM chatList WHERE chat.buyer == session.myID OR chat.seller == session.myID*/
			
		this.buyer = buyer;
		this.seller = seller;
		this.lastMsgId = lastMsgId;
	}

	public long getChatID() {
		return chatID;
	}

	public void setChatID(long chatID) {
		this.chatID = chatID;
	}

	public int getBuyer() {
		return buyer;
	}

	public void setBuyer(int buyerID) {
		this.buyer = buyerID;
	}

	public int getSeller() {
		return seller;
	}

	public void setSeller(int sellerID) {
		this.seller = sellerID;
	}

	public String getLastMsgId() {
		return lastMsgId;
	}

	public void setLastMsgId(String lastMsgId) {
		this.lastMsgId = lastMsgId;
	}


	
	
	
		
	
}
