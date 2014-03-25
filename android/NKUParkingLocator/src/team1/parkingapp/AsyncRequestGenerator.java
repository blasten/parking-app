package team1.parkingapp;

import android.app.IntentService;
import android.content.Intent;

public class AsyncRequestGenerator {

	private final static String BASE_URL = "http://parking-app.herokuapp.com/api/";
	private final static String USER_URL = "users/";
	
	
	private AsyncRequestGenerator()
	{
		
	}
	
	public static boolean verifyLogin(String username, String password)
	{
		return new AsyncRequestHandler().verifyUser(BASE_URL + USER_URL, username, password);
	}

}