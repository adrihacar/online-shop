package entities;

public class BougthsLogItem {
	
	private int idBougthItem;
	
	private int idLog;
	
	private int idProduct;
	
	private int idSeller;
	
	private int quantity;

	public BougthsLogItem(int idLog, int idProduct, int idSeller, int quantity) {
		this.idLog = idLog;
		this.idProduct = idProduct;
		this.idSeller = idSeller;
		this.quantity = quantity;
	}

	/**
	 * @return the idBougthItem
	 */
	public int getIdBougthItem() {
		return idBougthItem;
	}

	/**
	 * @param idBougthItem the idBougthItem to set
	 */
	public void setIdBougthItem(int idBougthItem) {
		this.idBougthItem = idBougthItem;
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
	 * @return the idSeller
	 */
	public int getIdSeller() {
		return idSeller;
	}

	/**
	 * @param idSeller the idSeller to set
	 */
	public void setIdSeller(int idSeller) {
		this.idSeller = idSeller;
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
