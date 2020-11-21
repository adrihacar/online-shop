package entities;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name="findChatsByUser",
			query="SELECT c FROM ChatBean c WHERE c.buyer= :user OR c.seller= :user ORDER BY c.lastMsgId DESC"),
	//@NamedQuery(name ="updateLastMsg",
	//		query="UPDATE ChatBean c SET c.lastMsgId = :latestMsgId")  // FIX MERGE, named query wasnt working and wasnt used
})
@Table(name="chats")
public class ChatBean {
	/** Id of the chat*/
	//All the relationships of this Entity are unidirectional
	
	@Id
	@GeneratedValue(strategy = AUTO)
	private long id;
	
	@Column(name="buyer")
	@NotNull
	private int buyer;
	
	@Column(name="seller")
	@NotNull
	private int seller;
	
	@Column(name="lastMsgId")
	@NotNull	
	private String lastMsgId;
	

	
	/** Empty constructor. Used by JPA */
	public ChatBean() {}
	
	/** Complete constructor */
	public ChatBean(long chatID, int buyer, int seller, String lastMsgId) {		
		this.id = chatID;
		this.buyer = buyer;
		this.seller = seller;
		this.lastMsgId = lastMsgId;
	}

	public long getChatID() {
		return id;
	}

	public void setChatID(long chatID) {
		this.id = chatID;
	}

	public int getBuyer() {
		return buyer;
	}
	
	public String getBuyerString() {
		return String.valueOf(buyer);
	}	

	public void setBuyer(int buyerID) {
		this.buyer = buyerID;
	}

	public int getSeller() {
		return seller;
	}
	
	public String getSellerString() {
		return String.valueOf(seller);
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
	


	
	
	
		
	
}
