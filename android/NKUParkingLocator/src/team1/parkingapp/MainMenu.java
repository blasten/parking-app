/*
 * This will handle the clicks on the Main Menu.  This is in a separate
 * class because the menu is the same on every activity so there is no
 * need to replicate the code on every one.
 * 
 */

package team1.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class MainMenu extends Activity {

	//Keep a reference to the Activity that you were on
	Activity a = null;
	
	//Constructor
	public MainMenu(Activity CallingActivity)
	{
		this.a = CallingActivity;
	}
	
	//This will actually handle the onClick Events
	public boolean handleOnClick(MenuItem item)
	{
		//
		switch(item.getItemId())
		{
		//If Login Was clicked then start the Login Activity
		case R.id.menuLogin:		//Login
			Intent i = new Intent(a,team1.parkingapp.LoginActivity.class);
			a.startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
