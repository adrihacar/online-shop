package entities;

public class UserBean { // extends org.w3c.tools.jdbc.JdbcBeanAdapter{
	
	private int id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String location;
	
	private String password;
	
	// Creates new UserBean
	public UserBean() {
	}

	public UserBean(String name, String surname, String username, String location, String password) {
		this.name = name;
		this.surname = surname;
		this.email = username;
		this.location = location;
		this.password = password;
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
	
		
	
}
