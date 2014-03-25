package team1.parkingapp;

import java.util.HashMap;

import android.app.IntentService;
import android.content.Intent;

public class RestService extends IntentService{

	private static final String baseUrl = "http://parking-app.herokuapp.com/api/";
	private static HashMap<String, Integer> requestTypes = null;
	
	public RestService()
	{
		super("Service to connect to NKU parking app");
		//Add Request Types here and put an integer as the value
		//Then add a case to the onHandleIntent action
		
		//Since this is static we only need to load it once
		if(requestTypes == null)
		{
			requestTypes = new HashMap<String, Integer>();
			requestTypes.put("LOGIN", 1);
		}
	}
	
	public RestService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String requestType = intent.getDataString();
		switch(requestTypes.get(requestType))
		{
			case 1:		//Login
				boolean isUser = verifyLogin(intent.getStringExtra("EMAIL"),intent.getStringExtra("PASSWORD"));
				if(isUser)
				{
					System.out.println("IS USER");
				}
				else
				{
					System.out.println("IS NOT USER");	
				}
				break;
			default:
				break;
		}
	}
	
	private boolean verifyLogin(String email, String password)
	{
		String result = "";
		try
		{
			String loginURL = this.baseUrl + "users/";
			//loginURL = "https://www.google.com";
			result = new AsyncRequestHandler("GET").execute(loginURL, email, password).get();
		}
		catch(Exception e)
		{
			result = "ERROR";
		}
		if(result == null)
			result = "ERROR";
		if(!result.equals("ERROR"))
		{
			if(result.toUpperCase().contains("ERROR") && result.toUpperCase().contains("INVALID ACCESS"))
			{
				result = "ERROR";
			}
		}
		return !result.equals("ERROR");
	}

}
