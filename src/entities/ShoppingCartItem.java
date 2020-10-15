package entities;

public class ShoppingCartItem {
	
	private int idCartItem;
	
	private int idCart;
	
	private int idItem;
	
	private int quantity;

	public ShoppingCartItem(int idCart, int idItem, int quantity) {
		this.idCart = idCart;
		this.idItem = idItem;
		this.quantity = quantity;
	}

	/**
	 * @return the idCartItem
	 */
	public int getIdCartItem() {
		return idCartItem;
	}

	/**
	 * @param idCartItem the idCartItem to set
	 */
	public void setIdCartItem(int idCartItem) {
		this.idCartItem = idCartItem;
	}

	/**
	 * @return the idCart
	 */
	public int getIdCart() {
		return idCart;
	}

	/**
	 * @param idCart the idCart to set
	 */
	public void setIdCart(int idCart) {
		this.idCart = idCart;
	}

	/**
	 * @return the idItem
	 */
	public int getIdItem() {
		return idItem;
	}

	/**
	 * @param idItem the idItem to set
	 */
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
