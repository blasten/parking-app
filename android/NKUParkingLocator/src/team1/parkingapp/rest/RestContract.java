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
	// There are none that can be placed into constants
	
	// Reservation API key-value parameters
	// POST & PUT parameters
	protected static final String SPOT_ID			=	"spot_id";
	protected static final String STATUS			=	"status";
}
