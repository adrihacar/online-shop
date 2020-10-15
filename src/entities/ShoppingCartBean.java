package entities;

public class ShoppingCartBean {
	
	private int idCart;
	
	private int idUser;
	
	public ShoppingCartBean() {}

	public ShoppingCartBean(int idUser) {
		this.idUser = idUser;
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

	

}
