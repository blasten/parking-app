/*
 * User.java
 * 4/16/14
 * Travis Carney
 * 
 * This class stores data about a user.
 */
package team1.parkingapp.data;

import org.json.JSONObject;

import team1.parkingapp.rest.RestContract;
import android.util.Log;

public class User {
	private int id;						// User's ID
	private String email;				// User's email address
	private String password;			// User's password (most likely boobs)
	private String name;				// User's first name
	private String lastName;			// User's last name
	private Reservation reservation;	// Current reservation
	
	public User(int id, String email, String password, String name, String lastName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.reservation = null;
	}
	
	/*
	 * Takes in a JSONObject containing user data and creates a user object from it.
	 * If any of the fields are null, default data is used.
	 */
	public static User validateJSONData(JSONObject json) {
		int id;
		String email, password, name, lastname;

		Log.i("Creating User from JSON", json.toString());
		
		if (json.has(RestContract.ERROR))
			return null;
		
		// Another disgusting wall of try catches
		try {
			id = json.getInt(RestContract.USER_ID);
		}
		catch (Exception e) {
			id = -1;
		}	
		try {
			email = json.getString(RestContract.USER_EMAIL);
		}
		catch (Exception e){
			email = "";
		}
		try {
			password = json.getString(RestContract.USER_PASSWORD);
		}
		catch (Exception e) {
			password = "";
		}		
		try {
			name = json.getString(RestContract.USER_PASSWORD);
		}
		catch (Exception e) {
			name = "";
		}		
		try {
			lastname = json.getString(RestContract.USER_LASTNAME);
		}
		catch (Exception e) {
			lastname = "";
		}
		
		return new User(id, email, password, name, lastname);
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

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
