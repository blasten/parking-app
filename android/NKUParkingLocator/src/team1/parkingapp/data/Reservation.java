/*
 * Reservation.java
 * 4/17/14
 * Travis Carney
 * 
 * This class stores data about a reservation.
 */
package team1.parkingapp.data;

import java.sql.Timestamp;

public class Reservation {
	private int id;				// Reservation ID
	private int userId;			// ID of the user making the reservation
	private String status;		// Current status of the reservation
	private Timestamp created;	// When the reservation was created
	private Timestamp updated;	// When the reservation was last updated
	
	public Reservation(int id, int userId, String status, Timestamp created, Timestamp updated) {
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.created = created;
		this.updated = updated;
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
