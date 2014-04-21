/*
 * RestContract.java
 * 4/16/14
 * Travis Carney
 * 
 * This class only contains constants that are used by the other REST classes.
 */
package team1.parkingapp.rest;

public final class RestContract {
	// API URL constants here
	protected static final String BASE_API_URL 		= 	"http://parking-app.herokuapp.com/api/";
	protected static final String USERS_API 		= 	BASE_API_URL + "users/";
	protected static final String SPOTS_API			= 	BASE_API_URL + "spots/";
	protected static final String LOTS_API			=	BASE_API_URL + "lots/";
	protected static final String RESERVATIONS_API	=	BASE_API_URL + "reservations/";
	
	// User API key-value parameters
	// POST parameters
	protected static final String EMAIL 			= 	"email";
	protected static final String PASSWORD			= 	"password";
	protected static final String NAME		 		=	"name";
	protected static final String LASTNAME			= 	"lastname";
	
	// Spots API key-value parameters
	// GET parameters
	protected static final String LAT1				= 	"lat1";
	protected static final String LONG1				= 	"long1";
	protected static final String LAT2				= 	"lat2";
	protected static final String LONG2				= 	"long2";
	
	// Spot Status strings
	protected static final String AVAILABLE			=	"AVAILABLE";
	protected static final String RESERVED			=	"RESERVED";
	protected static final String OCCUPIED			=	"OCCUPIED";
	protected static final String UNAVAILABLE		=	"UNAVAILABLE";
	
	// Lots API key-value parameters
	// API field names
	protected static final String LOT_ID			=	"id";
	protected static final String LOT_NAME			=	"name";
	protected static final String LOT_ENABLED		=	"enabled";
	protected static final String LOT_SPOTS_AVAIL	=	"num_spots_available";
	protected static final String LOT_LAT			=	"latitude";
	protected static final String LOT_LNG			=	"longitude";
	
	// Reservation API key-value parameters
	// API field names
	protected static final String SPOT_ID			=	"id";
	protected static final String SPOT_LOT_ID		=	"lot_id";
	protected static final String SPOT_LAT			=	"latitude";
	protected static final String SPOT_LONG			=	"longitude";
	protected static final String SPOT_STATUS		=	"status";
}
