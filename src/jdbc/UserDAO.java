package jdbc;

import java.util.List;

import entities.UserBean;

interface UserDAO {
	public boolean existClient(String  email);
	public void insertUser(UserBean user);
	public boolean checkUser(String email, String hashedPassword);
	public void updateUser(UserBean user, int id);
	public UserBean getUserdata(String id);
	public String getSaltFromDatabase(String email);
	int getIdFromEmail(String email);
	public List<UserBean> getAllUsers();
	public boolean isAdmin(int id); 
}
