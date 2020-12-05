package entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name="findChatsByUser",
			query="SELECT c FROM ChatBean c WHERE c.buyer= :user OR c.seller= :user ORDER BY c.lastMsgId DESC")
@Table(name="chats")
public class ChatBean {
	/** Id of the chat*/
	//All the relationships of this Entity are unidirectional
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	public long getChatId() {
		return id;
	}

	public void setChatId(long chatID) {
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

	public static ChatBean parse(UserChat userChat){
		ChatBean chatBean = new ChatBean();
		chatBean.setBuyer(userChat.getBuyer());
		chatBean.setSeller(userChat.getSeller());
		chatBean.setLastMsgId(userChat.getLastMsgId());
		
		return chatBean;
	}
	
	


	
	
	
		
	
}
