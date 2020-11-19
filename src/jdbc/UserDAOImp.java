package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.appserv.jdbc.DataSource;

import entities.UserBean;



public class UserDAOImp implements UserDAO{
	DataSource ds;
	public UserDAOImp(){
		Context ctx;
		try {
			ctx = new InitialContext();
			ds =(DataSource)ctx.lookup("TIWDS");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	//return if the user exists in the database
	public boolean existClient(String email) {
		boolean exist= false;
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				//check if there is a row with that value in the database
				st = con.createStatement();
				rs =st.executeQuery("SELECT email FROM users WHERE email='"+email+"'");
				
				if(rs.next()) {
					exist=true;
				}else {
					exist=false;
				}
				st.close();
			}
			con.close();
			
		}catch(Exception e) {
			
		}
		return exist;
	}

	@Override
	public void insertUser(UserBean user) {	
		Connection con;
		Statement st;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				st.executeUpdate("INSERT INTO users(name, surname, password, email, location, salt) VALUES ('" + user.getName() + "', '" + user.getSurname() +"', '"+ user.getPassword()+ "', '"  + user.getEmail()+"', '"+ user.getLocation() +"', '"+ user.getSalt()+"')");
				
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		

	@Override
	public boolean checkUser(String email, String hashedPassword) {
		boolean exist= false;
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				//check if there is a row with that value in the database
				st = con.createStatement();
				rs =st.executeQuery("SELECT email FROM users WHERE email='"+email+"' AND password='"+hashedPassword+"'");
				
				if(rs.next()) {
					exist=true;
				}else {
					exist=false;
				}
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			
		}
		return exist;
		
	}

	@Override
	public void updateUser(UserBean user, int id) {
		Connection con;
		Statement st;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				String query = "UPDATE users SET name ='" + user.getName() + "', surname ='" + user.getSurname() +"',password = '"+user.getPassword() + "', location ='"+user.getLocation()+ "' WHERE id="+id;
				System.out.println(query);
				st.executeUpdate(query);
				
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			
		}
	}
		

	@Override
	public UserBean getUserdata(int id) {
		Connection con;
		Statement st;
		ResultSet rs;
		UserBean user = null;
		
		int idd = 0;	
		String name = null;
		String surname = null;
		String email = null;
		String location = null;
		String password = null;
		String salt = null;
		
		
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM users WHERE id = "+ id);
				
				if(rs.next()) {
					idd= rs.getInt("id");	
					name=rs.getString("name");
					surname=rs.getString("surname");
					email=rs.getString("email");
					location=rs.getString("location");
					password=rs.getString("password");
					salt = rs.getString("salt");
				}
				st.close();
				con.close();
			}
				user = new UserBean();
				
				user.setEmail(email);
				user.setSalt(salt);
				user.setId(idd);
				user.setSurname(surname);
				user.setName(name);
				user.setPassword(password);
				user.setLocation(location);
				
		}catch(Exception e) {
			
		}
		return user;
	}

	@Override
	public String getSaltFromDatabase(String email) {
		Connection con;
		Statement st;
		ResultSet rs;
		String salt="";
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				System.out.println("entro en else, email: "+email);
				//check if there is a row with that value in the database
				st = con.createStatement();
				rs =st.executeQuery("SELECT salt FROM users WHERE email='"+email+"'");
				
				if(rs.next()) {
					salt= rs.getString("salt");		
				}
				rs.close();
				st.close();
			}
			con.close();
		}catch(Exception e) {
			
		}
		
		return salt;
		}
	
	@Override
	public int getIdFromEmail(String email) {
		Connection con;
		Statement st;
		ResultSet rs;
		int id= -1;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				//check if there is a row with that value in the database
				st = con.createStatement();
				rs =st.executeQuery("SELECT id FROM users WHERE email='"+email+"'");
				
				if(rs.next()) {
					id= rs.getInt("id");		
				}
				rs.close();
				st.close();
			}
			con.close();
		}catch(Exception e) {
			
		}
		
		return id;
		}
	
	@Override
	public List<UserBean> getAllUsers() {
		Connection con;
		Statement st;
		ResultSet rs;
		List<UserBean> users = new ArrayList<UserBean>();
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				rs =st.executeQuery("SELECT * FROM users");
				
				while(rs.next()) {
					UserBean userAux = new UserBean();
					userAux.setId(rs.getInt("id"));	
					userAux.setName(rs.getString("name"));
					userAux.setSurname(rs.getString("surname"));
					userAux.setEmail(rs.getString("email"));
					userAux.setLocation(rs.getString("location"));
					userAux.setPassword(rs.getString("password"));
					userAux.setSalt(rs.getString("salt"));
					users.add(userAux);		
				}
				rs.close();
				st.close();
			}
			con.close();
		}catch(Exception e) {
			
		}
		
		return users;
		}
	
	@Override
	public boolean isAdmin(int id) {
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			} else {
				st = con.createStatement();
				rs =st.executeQuery("SELECT id FROM users WHERE admin = 1");
				while(rs.next()) {
					if(id == rs.getInt("id")) {
						return true;
					}
				}
			}
		} catch(Exception e) {
			
		}
		return false;
	}

	@Override
	public void deleteUser(int id) {
		Connection con;
		Statement st;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				st.executeUpdate("DELETE FROM users WHERE id = "+id);
				
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
