package utils;

import entities.CartBean;
import entities.CartDAOImpl;



public class CreateCart {
	
	CartDAOImpl cartDAO = new CartDAOImpl("online_shop");
	
	public void createCart(int id) {
		if(id  == -1) {
			System.err.println("invalid id: -1");
		}else {
			CartBean cart = new CartBean();
			cart.setUser(id);
			cart.setBought(false);
			// the following parameters are set up on checkout
			cart.setAddress("");
			cart.setDate(-1);
			
			// insert it into the database
			try {
				cartDAO.insert(cart);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
