/*
 * This class will act as a factory to generate the HTTP
 * Requests for the NKU Parking App.  The constructor is 
 * private so that you have to use the methods provided.
 * There should be a separate method for each type of
 * request as well as private final static Strings for 
 * the different URLs.  This is to eliminate as much work
 * as possible when making the requests in the activities.
 * 
 */


package team1.parkingapp;

public class AsyncRequestGenerator {

	//These will keep track of the base url as well as the different pages
	private final static String BASE_URL = "http://parking-app.herokuapp.com/api/";
	private final static String USER_URL = "users/";
	
	//Reference to the async request handler to actually process the requests
	private final static AsyncRequestHandler requestHandler = new AsyncRequestHandler(); 
	
	private AsyncRequestGenerator()
	{
		//DO NOTHING
	}
	
	//This will return true if the user/password combo is in the database.
	public static boolean verifyLogin(String username, String password)
	{
		return requestHandler.verifyUser(BASE_URL + USER_URL, username, password);
	}

}