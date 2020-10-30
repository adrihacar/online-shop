package jdbc;

import entities.UserBean;

public interface UserDAO {
	public boolean existClient(String  email);
	public void insertUser(UserBean user);
	public void checkUser(String email, String hashedPassword);
	public void updateUser(UserBean user, int id);
	public UserBean getUserdata(String id);
}
