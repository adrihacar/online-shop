package utils;

import java.util.ArrayList;
import java.util.Iterator;

import entities.CartProductBean;

public class CartList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1L;

	public CartList() {
	}

	/**
	 * Retrieves the {@link CartProductBean} from this list whose
	 * {@link CartProductBean.product prductId} is equal to the parameter passed
	 * 
	 * 
	 * @param productId {@link CartProductBean.product prductId} of product to be
	 *                  found;
	 * @return A {@link CartProductBean} Object whose {@link CartProductBean.product
	 *         productId} is equal to the specified product in the parameters. Null
	 *         otherwise
	 */
	public CartProductBean getProductById(int productId) {
		CartProductBean productFound = null;

		@SuppressWarnings("unchecked")
		Iterator<CartProductBean> cartproductsIterator = (Iterator<CartProductBean>) this.iterator();
		while (cartproductsIterator.hasNext()) {

			CartProductBean listedProduct = cartproductsIterator.next();
			if (listedProduct.getProduct() == productId) {
				productFound = listedProduct;
				break;
			}
		}
		return productFound;
	}
}
