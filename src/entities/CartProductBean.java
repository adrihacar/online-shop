package entities;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name="findProductsInCart",
		query="SELECT cp FROM CartProductBean cp WHERE cp.cart=:cart")
})
@Table(name="cartproducts")
public class CartProductBean {
	
	// Attributes
	@Id
	@GeneratedValue (strategy = AUTO)
	private int id;
	
	@Column(name="cart")
	@NotNull
	private int cart;
	
	@Column(name="product")
	@NotNull
	private int product;
	
	@Column(name="quantity")
	@NotNull
	private int quantity;

	
	// Constructors
	public CartProductBean() {

	}
	
	public CartProductBean(int idCart, int idItem, int quantity) {
		this.cart = idCart;
		this.product = idItem;
		this.quantity = quantity;
	}

	
	// Getters and setters
	public int getId() {
		return id;
	}

	public int getCart() {
		return cart;
	}

	public void setCart(int cart) {
		this.cart = cart;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	


	
	

}
