package team1.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class MainMenu extends Activity {

	Activity a = null;
	
	public MainMenu(Activity CallingActivity)
	{
		this.a = CallingActivity;
	}
	
	public boolean handleOnClick(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.menuLogin:
			Intent i = new Intent(a,team1.parkingapp.LoginActivity.class);
			a.startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
