package entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import utils.CartList;

public class UserCart extends CartBean {
	public static final int PRICE_PRECISION = 2;
	private List<ProductBean> productsInfo;
	private CartList<CartProductBean> productsInCart;
	private int size;
	private int cartId;

	public UserCart() {

	}

	public UserCart(int user) {
		super(user);

	}

	public UserCart(CartBean cart, List<ProductBean> productsInfo, CartList<CartProductBean> productsInCart)
			throws Exception {
		if (productsInfo.size() != productsInCart.size())
			throw new Exception("The args 'productsInfo' and 'productsInCart' must have the same size ");

		this.setUser(cart.getUser());
		this.setAddress(cart.getAddress());
		this.setBought(cart.isBought());
		this.setPaymethod(cart.getPaymethod());
		this.setDate(cart.getDate());

		this.productsInfo = productsInfo;
		this.productsInCart = productsInCart;
		this.size = productsInfo.size();
		this.cartId = cart.getId();

	}

	/**
	 * Updates the information of the calling cart with the Cart information of the
	 * user in the Database
	 * 
	 * @throws Exception generated when trying to update this UserCart with the cart
	 *                   information from the database
	 * 
	 */
	public void updateToCurrentCart() throws Exception {
		try {
			// DAOs
			CartDAOImpl cartDAO = new CartDAOImpl("online_shop");

			// get cart of user
			CartBean cart = cartDAO.findCartByUser(this.getUser());

			this.setCartInfo(cart);
		} catch (Exception e) {
			throw e;

		}
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

	public String getCartPrice(int precision) {
		String formater = "%." + precision + "f";
		return String.format(formater, this.getCartPrice());
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

	public String getProductPrice(int index, int precision) {
		String formater = "%." + precision + "f";
		return String.format(formater, this.getProductPrice(index));

	}

	public double getProductTotalCost(int index) {
		return getProductPrice(index) * productsInCart.get(index).getQuantity();
	}

	public String getProductTotalCost(int index, int precision) {
		String formater = "%." + precision + "f";
		return String.format(formater, this.getProductTotalCost(index));

	}

	public String getProductDescription(int index) {
		return productsInfo.get(index).getDescription();
	}

	public byte[] getProductImage(int index) {
		return productsInfo.get(index).getImage();
	}

	public int getProductId(int index) {
		return productsInfo.get(index).getId();
	}

	/**
	 * Obtains the formatted purchase date of this UserCart
	 * 
	 * @return The purchase date of this UserCart, using the format dd/mm/yyyy
	 */
	public String getDateToString() {
		String date = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.getDate());
		// Calendar represents the month with indexes from 0 to 11
		// Thus adding one the real month number is displayed
		int month = cal.get(Calendar.MONTH) + 1;
		date += cal.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + cal.get(Calendar.YEAR);

		return date;
	}

	/**
	 * Obtains the list of products and all the cart information from the given
	 * {@link CartBean} object
	 * 
	 * @param cart {@link CartBean} with the information to be set in this UserCart
	 * @Overrides
	 * @throws Exception generated when trying to set the cart attributes
	 */
	@Override
	public void setCartInfo(CartBean cart) throws Exception {
		try {
			// DAOs
			CartProductDAOImpl cartProductDAO = new CartProductDAOImpl("online_shop");
			ProductDAOImpl productDAO = new ProductDAOImpl("online_shop");

			// Retrieve the products inside the cart, if any
			CartList<CartProductBean> cartProductList = new CartList<CartProductBean>();
			cartProductList.addAll(cartProductDAO.findProductsInCart(cart.getId()));

			// retrieve the products info
			List<ProductBean> productsInfo = new ArrayList<>();
			Iterator<CartProductBean> cartProductIterator = cartProductList.iterator();
			while (cartProductIterator.hasNext()) {
				int productid = cartProductIterator.next().getProduct();
				ProductBean product = productDAO.findByID(productid);
				productsInfo.add(product);
			}
			super.setCartInfo(cart);
			if (productsInfo.size() != cartProductList.size())
				throw new Exception("The args 'productsInfo' and 'productsInCart' must have the same size ");
			this.productsInfo = productsInfo;
			this.productsInCart = cartProductList;
			this.size = productsInfo.size();
			this.cartId = cart.getId();

		} catch (Exception e) {
			throw e;

		}

	}

	/**
	 * Modifies the quantity of a determined product of the list. If the product is
	 * not in the current cart, it is added. If the product is already in the cart,
	 * only its quantity is modify
	 * 
	 * @param productId Id of the product to modify
	 * @param quantity  Quantity of the specified product
	 * @param resetQuantity True if the previous quantity is ignored and it is set to the new one. False if the new quantity should be added to the previous value
	 * @throws Exception Exception generated when trying to modify the product
	 *                   information
	 */
	public void modifyProduct(int productId, int quantity, boolean resetQuantity) throws Exception {
		try {
			// DAOs
			CartProductDAOImpl cartProductDAO = new CartProductDAOImpl("online_shop");

			CartProductBean productToAdd = new CartProductBean(this.cartId, productId, quantity);

			// Check if the selected product had been added previously to the cart
			if (this.productsInCart.contains(productToAdd)) {				
				// The product is already in the cart. Update the quantity
				productToAdd = productsInCart.getProductById(productId);
				if (resetQuantity) {
					productToAdd.setQuantity(quantity);
				} else {
					productToAdd.modifyQuantity(quantity);
				}

				cartProductDAO.update(productToAdd);

			} else {
				// the product is not in the cart
				// new CartProduct object is created and added to the cart
				cartProductDAO.insert(productToAdd);
				this.size++;
			}
			this.updateToCurrentCart();
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Deletes a product from the CartProducts list
	 * 
	 * @param productId Product to be deleted
	 * @throws Exception Throws a new exception if the product specified in the
	 *                   parameters is not within this UserCart product list
	 */
	public void deleteProduct(int productId) throws Exception {
		try {
			// DAOs
			CartProductDAOImpl cartProductDAO = new CartProductDAOImpl("online_shop");

			CartProductBean productToAdd = new CartProductBean(this.cartId, productId, 0);

			// Check if the selected product had been added previously to the cart
			if (this.productsInCart.contains(productToAdd)) {

				// The product has been found in the list. Remove it.
				productToAdd = productsInCart.getProductById(productId);
				cartProductDAO.delete(productToAdd);
				this.updateToCurrentCart();				
			} else {
				// the product is not in the cart
				// throw a new Exception
				throw new Exception("The specified product does not belong to this list");
			}
		} catch (Exception e) {
			throw e;
		}

	}

}
