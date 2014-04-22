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
	public static final String BASE_API_URL 			= 	"http://parking-app.herokuapp.com/api/";
	public static final String USERS_API 			= 	BASE_API_URL + "users/";
	public static final String SPOTS_API				= 	BASE_API_URL + "spots/";
	public static final String LOTS_API				=	BASE_API_URL + "lots/";
	public static final String RESERVATIONS_API		=	BASE_API_URL + "reservations/";
	
	// User API key-value parameters
	// POST parameters
	public static final String USER_EMAIL 			= 	"email";
	public static final String USER_PASSWORD			= 	"password";
	public static final String USER_NAME		 		=	"name";
	public static final String USER_LASTNAME			= 	"lastname";
	public static final String USER_ID				=	"id";

	
	// Spots API key-value parameters
	// GET parameters
	public static final String SPOT_LAT1				= 	"lat1";
	public static final String SPOT_LONG1			= 	"long1";
	public static final String SPOT_LAT2				= 	"lat2";
	public static final String SPOT_LONG2			= 	"long2";
	public static final String SPOT_ID				= 	"id";
	public static final String SPOT_STATUS			=	"status";
	public static final String SPOT_LATITUDE			= 	"latitude";
	public static final String SPOT_LONGITUDE		=	"longitude";
	public static final String SPOT_LOT_ID			=	"lot_id";


	
	// Spot Status strings
	public static final String AVAILABLE				=	"AVAILABLE";
	public static final String RESERVED				=	"RESERVED";
	public static final String OCCUPIED				=	"OCCUPIED";
	public static final String UNAVAILABLE			=	"UNAVAILABLE";
	
	// Lots API key-value parameters
	// API field names
	public static final String LOT_ID				=	"id";
	public static final String LOT_NAME				=	"name";
	public static final String LOT_ENABLED			=	"enabled";
	public static final String LOT_SPOTS_AVAIL		=	"num_spots_available";
	public static final String LOT_LAT				=	"latitude";
	public static final String LOT_LNG				=	"longitude";
	
	// Reservation API key-value parameters
	// API field names
	public static final String RESERVATION_ID		=	"id";
	public static final String RESERVATION_USER_ID	=	"user_id";
	public static final String RESERVATION_SPOT_ID	=	"spot_id";
	public static final String RESERVATION_STATUS	=	"status";

	
	// Miscellaneous API constants
	public static final String ERROR					=	"error";
}
