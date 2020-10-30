package entities;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class UserBean { // extends org.w3c.tools.jdbc.JdbcBeanAdapter{
	
	private int id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String location;
	
	private String password;
	
	private String salt;
	
	// Creates new UserBean
	public UserBean() {
	}

	public UserBean(String name, String surname, String email, String location, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.location = location;

		
		/*Create a random salt with class SecureRandom*/
		SecureRandom random = new SecureRandom();
		byte[] num = new byte[16];
		random.nextBytes(num);
		String salt=Base64.getEncoder().encodeToString(num); /*Encode the bytes generated in Base64 printable Strings*/
		
		this.setSalt(salt);
		this.password= getSHA256Hash(password,salt);
		
	}
	
	public static String getSHA256Hash(String data, String salt) {
		 String result = null;
		 try {
			 /*Create Object digest*/
		     MessageDigest digest = MessageDigest.getInstance("SHA-256");
		     /*Add salt to object*/
		     digest.update(salt.getBytes());
		     /*Add the data(password) to object*/
		     byte[] hash = digest.digest(data.getBytes("UTF-8"));
		     /*Get the hash and encode in Base64*/
		     result = Base64.getEncoder().encodeToString(hash);
		            
		            
		 }catch(Exception ex) {
		      ex.printStackTrace();
		 }
		 return result;
		
		 }
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the username
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param username the username to set
	 */
	public void setEmail(String username) {
		this.email = username;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
		
	
}
