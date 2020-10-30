package entities;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;


@Entity
@Table(name="carts")
public class CartBean {
	
	// Attributes
	@Id
	@GeneratedValue (strategy = AUTO)
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
		return user;
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

	public void setDate(int date) {
		this.date = date;
	}


}
