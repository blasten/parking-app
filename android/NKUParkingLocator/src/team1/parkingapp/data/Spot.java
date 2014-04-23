/*
 * Spot.java
 * 4/16/14
 * Travis Carney
 * 
 * This class stores data about a parking spot.
 */
package team1.parkingapp.data;

import org.json.JSONObject;

import android.util.Log;

import team1.parkingapp.rest.RestContract;

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
	 * Pulls out the data from each JSON object and returns a SPOT object from that data.
	 * If any fields in the database are null, they are set to default invalid values.
	 */
	public static Spot validateJSONData(JSONObject json) {
		int id, lot_id;
		double lat, lng;
		String status;
		
		Log.i("Spot JSON", json.toString());
		
		// Begin the disgusting wall of try-catches
		try {
			id = json.getInt(RestContract.SPOT_ID);
		}
		catch (Exception e) {
			id = -1;
		}
		try {
			lot_id = json.getInt(RestContract.SPOT_LOT_ID);
		}
		catch (Exception e) {
			lot_id = -1;
		}
		try {
			lat = json.getDouble(RestContract.SPOT_LATITUDE);
		}
		catch (Exception e) {
			lat = 0;
		}
		try {
			lng = json.getDouble(RestContract.SPOT_LONGITUDE);
		}
		catch (Exception e) {
			lng = 0;
		}
		try {
			status = json.getString(RestContract.SPOT_STATUS);
		}
		catch (Exception e) {
			status = "";
		}
		
		return new Spot(id, lot_id, lat, lng, status);
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
