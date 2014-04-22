/*
 * Reservation.java
 * 4/17/14
 * Travis Carney
 * 
 * This class stores data about a reservation.
 */
package team1.parkingapp.data;

import org.json.JSONObject;

import team1.parkingapp.rest.RestContract;
import android.util.Log;

public class Reservation {
	private int id;				// Reservation ID
	private int userId;			// ID of the user making the reservation
	private String status;		// Current status of the reservation
	private int spotId;			// Spot ID of the reservation
	
	public Reservation(int id, int userId, String status, int spotId) {
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.spotId = spotId;
	}
	
	/*
	 * Takes in a JSONObject containing reservation data and creates a reservation object from it.
	 * If any of the fields are null, default data is used.
	 */
	public static Reservation validateJSONData(JSONObject json) {
		int id, userId, spotId;
		String status;

		Log.i("Creating Reservaton from JSON", json.toString());
		
		// Another disgusting wall of try catches
		try {
			id = json.getInt(RestContract.RESERVATION_ID);
		}
		catch (Exception e) {
			id = -1;
		}	
		try {
			userId = json.getInt(RestContract.RESERVATION_USER_ID);
		}
		catch (Exception e){
			userId = -1;
		}
		try {
			status = json.getString(RestContract.RESERVATION_STATUS);
		}
		catch (Exception e) {
			status = "";
		}
		try
		{
			spotId = json.getInt(RestContract.RESERVATION_SPOT_ID);
		}
		catch(Exception e)
		{
			spotId = -1;
		}
		
		return new Reservation(id, userId, status, spotId);
	}

	/*
	 * Accessors and mutators follow.
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSpotId()
	{
		return spotId;
	}
	
	public void setSpotId(int spotId)
	{
		this.spotId = spotId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
