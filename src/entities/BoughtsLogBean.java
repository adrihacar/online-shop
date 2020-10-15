package entities;

public class BoughtsLogBean {
	
	private int idLog;
	
	private int idCart;

	public BoughtsLogBean(int idCart) {
		this.idCart = idCart;
	}

	/**
	 * @return the idLog
	 */
	public int getIdLog() {
		return idLog;
	}

	/**
	 * @param idLog the idLog to set
	 */
	public void setIdLog(int idLog) {
		this.idLog = idLog;
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
	
	

}
