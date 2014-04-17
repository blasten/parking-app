/*
 * User.java
 * 4/16/14
 * Travis Carney
 * 
 * This class stores data about a user.
 */
package team1.parkingapp.data;

public class User {
	private int id;				// User's ID
	private String email;		// User's email address
	private String password;	// User's password (most likely boobs)
	private String name;		// User's first name
	private String lastName;	// User's last name
	
	public User(int id, String email, String password, String name, String lastName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
	}

	/*
	 * Accessors and mutators follow.
	 */
	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.name + " " + this.lastName;
	}
}
