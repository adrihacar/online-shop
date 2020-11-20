package entities;

import java.util.Calendar;
import java.util.List;

public class PurchasedCart extends CartBean {
	private List<ProductBean> productsInfo;
	private List<CartProductBean> productsInCart;
	private int size;
	
	public PurchasedCart() {
		
	}

	public PurchasedCart(int user) {
		super(user);
		
	}
	
	public PurchasedCart(CartBean cart, List<ProductBean> productsInfo, List<CartProductBean> productsInCart) throws Exception {
		if(productsInfo.size()!=productsInCart.size()) throw new Exception("The args 'productsInfo' and 'productsInCart' must have the same size ");
		
		this.setUser(cart.getUser());
		this.setAddress(cart.getAddress());
		this.setBought(cart.isBought());
		this.setPaymethod(cart.getPaymethod());
		this.setDate(cart.getDate());
		
		this.productsInfo = productsInfo;
		this.productsInCart = productsInCart;
		this.size = productsInfo.size();
		
	}

	public int size() {
		return size;
	}
	
	
	public double getCartPrice() {
		double cartPrice = 0;

		for (int i = 0; i < this.productsInfo.size(); i++) {
			cartPrice += this.productsInCart.get(i).getQuantity() * this.productsInfo.get(i).getPrice();
		}
		return cartPrice;
	}
		
	public String getProductName(int index) {		
		return productsInfo.get(index).getName();		
	}
	
	
	public int getProductQuantity(int index) {
		return productsInCart.get(index).getQuantity();
	}
	
	public double getProductPrice(int index) {
		return productsInfo.get(index).getPrice();
	}
	
	public double getProductTotalCost(int index) {
		return getProductPrice(index)*productsInCart.get(index).getQuantity();
	}
	
	public String getProductDescription(int index) {
		return productsInfo.get(index).getDescription();
	}
	
	public byte[] getProductImage(int index) {
		return productsInfo.get(index).getImage();
	}
	
	public String getDateToString() {
		String date = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.getDate());
		//Calendar represents the month with indexes from 0 to 11
		//Thus adding one the real month number is displayed 
		int month = cal.get(Calendar.MONTH) + 1; 
		date += cal.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + cal.get(Calendar.YEAR);
		
				
		return date;
	}
	
	
				
}
