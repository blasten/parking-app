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
	public static final String BASE_API_URL 	= 	"http://parking-app.herokuapp.com/api/";
	public static final String USERS_API 		= 	BASE_API_URL + "users/";
	public static final String SPOTS_API		= 	BASE_API_URL + "spots/";
	public static final String LOTS_API			=	BASE_API_URL + "lots/";
	public static final String RESERVATIONS_API	=	BASE_API_URL + "reservations/";
	
	// User API key-value parameters
	// POST parameters
	public static final String USER_EMAIL 		= 	"email";
	public static final String USER_PASSWORD	= 	"password";
	public static final String USER_NAME		 =	"name";
	public static final String USER_LASTNAME	= 	"lastname";
	public static final String USER_ID			=	"id";
	
	// Spots API key-value parameters
	// GET parameters
	public static final String LAT1				= 	"lat1";
	public static final String LONG1			= 	"long1";
	public static final String LAT2				= 	"lat2";
	public static final String LONG2			= 	"long2";
	// Response fields
	public static final String SPOT_ID			=	"id";
	public static final String SPOT_LOT_ID		=	"lot_id";
	public static final String SPOT_LAT			=	"latitude";
	public static final String SPOT_LONG		=	"longitude";
	public static final String SPOT_STATUS		=	"status";
	
	// Spot Status strings
	public static final String AVAILABLE		=	"AVAILABLE";
	public static final String RESERVED			=	"RESERVED";
	public static final String OCCUPIED			=	"OCCUPIED";
	public static final String UNAVAILABLE		=	"UNAVAILABLE";
	
	// Lots API key-value parameters
	public static final String LOT_ID			=	"id";
	public static final String LOT_NAME			=	"name";
	public static final String LOT_ENABLED		=	"enabled";
	public static final String LOT_SPOTS_AVAIL	=	"num_spots_available";
	public static final String LOT_LAT			=	"latitude";
	public static final String LOT_LNG			=	"longitude";
	
	// Reservation API key-value parameters
	public static final String RES_ID			=	"id";
	public static final String RES_USER			= 	"user_id";
	public static final String RES_SPOT			=	"spot_id";
	public static final String RES_STATUS		=	"status";
	public static final String RES_CREATED		=	"created_at";
	public static final String RES_UPDATED		=	"updated_at";
	
	// Miscellaneous API constants
	public static final String ERROR			=	"error";
}
