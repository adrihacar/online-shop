package entities;

import static javax.persistence.GenerationType.IDENTITY;


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
	@NamedQuery(name="findCartByUser",
		query="SELECT c FROM CartBean c WHERE c.user=:user AND c.bought=false"),
	
	@NamedQuery(name="findBoughtCartsByUser",
		query="SELECT c FROM CartBean c WHERE c.user=:user AND c.bought=true ORDER BY c.date"),
	
	@NamedQuery(name="buyCart",
		query="UPDATE CartBean c SET c.bought=true, c.date=date WHERE c.id=:id"), 
	
	@NamedQuery(name="updateCart",
		query="UPDATE CartBean c SET c.paymethod=paymethod, c.address=address WHERE c.id=:id")
})
@Table(name="carts")
public class CartBean {
	
	// Attributes
	@Id
	@GeneratedValue (strategy = IDENTITY)
	private int id;
	
	@Column(name="user")
	@NotNull
	private int user;
	
	@Column(name="address")
	@NotNull
	private String address;
	
	@Column(name="paymethod")
	@NotNull
	private int paymethod;
	
	@Column(name="bought")
	@NotNull
	private boolean bought;
	
	@Column(name="date")
	@NotNull
	private long date; // UNIX timestamp
	
	// Constructors
	public CartBean() {}

	public CartBean(int user) {
		this.user = user;
	}
	
	//Getters and setters
	public int getId() {
		return id;
	}
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(int paymethod) {
		this.paymethod = paymethod;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	/**
	 * Sets the cart information of the calling UserCart
	 * 
	 * @param cart {@link CartBean} with the information to be set in this UserCart
	 * @throws Exception generated when trying to set the cart attributes
	 */
	public void setCartInfo(CartBean cart) throws Exception {		
		try {
			this.user = cart.getUser();
			this.setAddress(cart.getAddress());
			this.setBought(cart.isBought());
			this.setPaymethod(cart.getPaymethod());
			this.setDate(cart.getDate());
			
	
		} catch (Exception e) {
			throw e;
		}
		
	}

}
