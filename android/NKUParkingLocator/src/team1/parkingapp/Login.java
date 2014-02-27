package team1.parkingapp;

import java.util.HashMap;

//This class will handle the login database and verification
public class Login {
	
	//This will be the "database"
	private static HashMap<String, String> map = new HashMap<String, String>();
	
	//Check to see if the parameter is a user
	public static boolean isUser(String username)
	{
		return map.containsKey(username);
	}
	
	//Check to see if the username as password is in the database
	public static boolean verifyPassword(String username, String password)
	{
		return isUser(username) && map.get(username).equals(password);
	}
	
	//This will add a user and return true if added and false if the user already exists
	public static boolean addUser(String username, String password)
	{
		if(isUser(username))
			return false;
		map.put(username, password);
		return true;
	}
}