/*
 * Reservation.java
 * 4/17/14
 * Travis Carney
 * 
 * This class stores data about a reservation.
 */
package team1.parkingapp.data;

import java.sql.Timestamp;

import org.json.JSONObject;

import team1.parkingapp.rest.RestContract;
import android.util.Log;

public class Reservation {
	private int id;				// Reservation ID
	private int userId;			// ID of the user making the reservation
	private int spotId;			// ID of the spot being reserved
	private String status;		// Current status of the reservation
	private Timestamp created;	// When the reservation was created
	private Timestamp updated;	// When the reservation was updated
	
	public Reservation(int id, int userId, int spotId, String status, Timestamp created, Timestamp updated) {
		this.id = id;
		this.userId = userId;
		this.spotId = spotId;
		this.status = status;
		this.created = created;
		this.updated = updated;
	}
	
	/*
	 * Takes in a JSONObject containing reservation data and creates a reservation object from it.
	 * If any of the fields are null, default data is used.
	 */
	public static Reservation validateJSONData(JSONObject json) {
		int id, userId, spotId;
		String status;
		Timestamp created, updated;

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
			spotId = json.getInt(RestContract.RESERVATION_SPOT_ID);
		}
		catch (Exception e) {
			spotId = -1;
		}
		try {
			status = json.getString(RestContract.RESERVATION_STATUS);
		}
		catch (Exception e) {
			status = "";
		}
		try
		{
			created = Timestamp.valueOf(json.getString(RestContract.RESERVATION_CREATED));
		}
		catch(Exception e)
		{
			created = null;
		}
		try {
			updated = Timestamp.valueOf(json.getString(RestContract.RESERVATION_UPDATED));
		}
		catch (Exception e) {
			updated = null;
		}
		
		return new Reservation(id, userId, spotId, status, created, updated);
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
}
