/*
 * Spot.java
 * 4/16/14
 * Travis Carney
 * 
 * This class stores data about a parking spot.
 */
package team1.parkingapp.data;

public class Spot {
	private int id;				// The spot's ID
	private int lotId;			// ID of the lot containing the spot
	private double lat;			// Latitude of the spot
	private double longitude;	// Longitude of the spot
	private String status;		// Current status of the spot
	
	public Spot(int id, int lotId, double lat, double longitude, String status) {
		this.id = id;
		this.lotId = lotId;
		this.lat = lat;
		this.longitude = longitude;
		this.status = status;
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

	public int getLotId() {
		return lotId;
	}

	public void setLotId(int lotId) {
		this.lotId = lotId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
