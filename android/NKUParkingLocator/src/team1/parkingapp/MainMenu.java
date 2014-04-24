/*
 * This will handle the clicks on the Main Menu.  This is in a separate
 * class because the menu is the same on every activity so there is no
 * need to replicate the code on every one.
 * 
 */

package team1.parkingapp;

import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

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
		Intent i;
		switch(item.getItemId())
		{
			//If Login Was clicked then start the Login Activity
			case R.id.menuLogin:		//Login
				i = new Intent(a,team1.parkingapp.LoginActivity.class);
				a.startActivityForResult(i, 1);
				return true;
			case R.id.menuEditUser:		//Edit User Information
				i = new Intent(a, team1.parkingapp.AccountSettingsActivity.class);
				a.startActivityForResult(i, 1);
				return true;
			case R.id.menuLogout:
				Session.getInstance().setUser(null);
				a.invalidateOptionsMenu();
				Toast.makeText(a, R.string.logged_out, Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		a.invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
