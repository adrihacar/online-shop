package entities;

public class ShoppingCartBean {
	
	private int idProduct;
	
	private int idUser;
	
	private int cuantity;
	
	public ShoppingCartBean() {}

	public ShoppingCartBean(int idProduct, int idUser, int cuantity) {
		this.idProduct = idProduct;
		this.idUser = idUser;
		this.cuantity = cuantity;
	}

	/**
	 * @return the idProduct
	 */
	public int getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the cuantity
	 */
	public int getCuantity() {
		return cuantity;
	}

	/**
	 * @param cuantity the cuantity to set
	 */
	public void setCuantity(int cuantity) {
		this.cuantity = cuantity;
	}
	
	

}
