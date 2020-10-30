package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				rs =st.executeQuery("SELECT email FROM USERS WHERE email="+email);
				
				if(rs.first()) {
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
	public void insertUser(UserBean user) {	
		Connection con;
		Statement st;
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				st.executeUpdate("INSERT INTO USERS(name, surname, password, email, location) VALUES ('" + user.getName() + "', '" + user.getSurname() +"', '"+ user.getPassword()+ "', '"  + user.getEmail()+"', '"+ user.getLocation() +"')");
				
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			
		}
	}
		

	@Override
	public void checkUser(String email, String hashedPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserBean user, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserBean getUserdata(String id) {
		Connection con;
		Statement st;
		ResultSet rs;
		UserBean user;
		
		try {
			con =ds.getConnection();
			
			if(con == null) {
				System.out.println("---->UNABLE TO CONNECT TO SERVER:");
			}else {
				st = con.createStatement();
				rs = st.executeQuery("SELECT email FROM USERS WHERE id = "+ id);
				
				while(rs.first()) {
					int idd= rs.getInt("id");	
					String name=rs.getString("name");
					String surname=rs.getString("surname");
					String email=rs.getString("email");
					String location=rs.getString("location");
					String password=rs.getString("password");
				}
				st.close();
				con.close();
			}
			
		}catch(Exception e) {
			
		}
		return null;
	}

}
