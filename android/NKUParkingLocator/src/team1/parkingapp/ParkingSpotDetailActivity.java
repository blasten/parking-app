/*
 * ParkingSpotDetailActivity.java
 * 4/19/14
 * Travis Carney, Mat Ferry, Todd Phillips
 * 
 * This file contains the code to run the detail parking spot screen.
 * This screen will also handle making reservations, and calling the navigator. 
 */

package team1.parkingapp;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Spot;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingSpotDetailActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) { 
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.parking_spot_detail);
		    int intLotID;
		    // update the title
		    String TitleScreen = getIntent().getExtras().getString("GarageTitle");
  	      	TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
  	      	txtTitle.setText( TitleScreen); 
		    
  	      	// Clear the stars
  	      	ClearSpots();
  	      
  	      	// Get the lot ID
  	        intLotID = getLotID(TitleScreen);	
  	      	
  	      	// Display the spots for this lot The Rest API is not currently returning the spots so this does not wok atm
  	      	DisplaySpots(intLotID);
  	      	
  	      	
  	      	// May need to use this later if the spot updating process takes to long 
  	      	// and the app times out so please leave this here...
		    //new UpdateTitle().execute();
		    
	  }
	  
	  // Remove all stars from layout.
	  public void ClearSpots()
	  {
		  ImageView star; 
		  for(int i = 1; i <= 36; i++)
		  {
			  star = (ImageView) findViewById(getResources().getIdentifier("spot" + i, "id", "team1.parkingapp"));
			  star.setVisibility(star.INVISIBLE);
		  }
	  }
	  
	  public void DisplaySpots(int intLotNumber)
	  {
		  Vector<Spot> spots;
		  spots = RestTaskFactory.getSpotsByLot(this,intLotNumber );
		  
	  }
	  
	  public int getLotID(String strTitle)
	  {
		  	int intLotID = 0;
		  	Vector<ParkingLot> lots = Session.getInstance().getParkingLots();
      		for(int i = 0 ; i < lots.size() ; ++i)
      		{
      			if(strTitle.equals(lots.get(i).getName()))
      			{
      				intLotID = lots.get(i).getId();
      			}
      		}
      		return intLotID;
	  }

	 public boolean onOptionsItemSelected(MenuItem item)
	 {
		 return new MainMenu(this).handleOnClick(item);
	 }
	  
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  // Inflate the menu; this adds items to the action bar if it is present.
		  getMenuInflater().inflate(R.menu.main, menu);
		  return true;
	  }
	  /* We may have to use this if the spot process takes to long so im saving this here for now.. 
	     If we find we don't need it we can always just take it out... 
	  private class UpdateTitle extends AsyncTask<Void, String, Void>  {
		  // Update UI here
		  @Override
		  protected void onProgressUpdate(String... values) {
	  	      	TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
	  	      	txtTitle.setText( values[0]);   
	  	      	// Force view to update...
	  	      	//txtTitle.invalidate();

		  }

		  // Get data here... 
		  @Override
		  protected Void doInBackground(Void... params) {
			  // Pull out the extra information passed through the intent 
		  	  String TitleScreen = getIntent().getExtras().getString("GarageTitle");
		  	  publishProgress( TitleScreen);
		  	  return null;
		  }
		  @Override
		  protected void onPostExecute(Void result) {
		    // TODO Auto-generated method stub
		    super.onPostExecute(result);
		  }

		  @Override
		  protected void onPreExecute() {
		    // TODO Auto-generated method stub
		    super.onPreExecute();
		  }

		
		 }*/
}


