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
	@NamedQuery(name="findProductsInCart",
		query="SELECT cp FROM CartProductBean cp WHERE cp.cart=:cart")
})
@Table(name="cartproducts")
public class CartProductBean {
	
	// Attributes
	@Id
	@GeneratedValue (strategy = IDENTITY)
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
	
	/**
	 * Adds the specified quantum to this {@link CartProductBean} quantity.
	 * 
	 * @param quantum Quantity to be added to this.quantity value. Cannot be negative
	 */
	public void modifyQuantity(int quantum) {
		this.setQuantity(this.getQuantity() + quantum);

	}

	/**
	 * Compares this object to the specified object. The result is true if and only
	 * if the argument is not null and is a {@link CartProductBean} object that
	 * contains the same productId value as this object.
	 * 
	 * @Overrides:equals in class Object
	 * @Returns:true if the objects are the same; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CartProductBean))
			return false;
		if (obj == this)
			return true;
		return (this.getCart() == ((CartProductBean) obj).getCart()
				&& this.getProduct() == ((CartProductBean) obj).getProduct());
	}

}
