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

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Spot;
import team1.parkingapp.rest.RestContract;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ParkingSpotDetailActivity extends Activity {
	
	 private Location CurrentLocation; 
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
		  ImageView star; 
		  Vector<Spot> spots;
		  spots = RestTaskFactory.getSpotsByLot(this,intLotNumber );
		  for(int i = 0; i < spots.size();i++)
		  {
			  // Is the spot occupied?
			  if(spots.get(i).getStatus().equals("AVAILABLE") )
			  {
				  star = (ImageView) findViewById(getResources().getIdentifier("spot" + (i + 1) , "id", "team1.parkingapp"));
				  star.setVisibility(star.VISIBLE);
				  
				  // Store spot information so that we may reserve the spot later if needed.
				  star.setTag(spots.get(i));
				  
				  star.setClickable(true);
				  star.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ImageView star = (ImageView) v;
						Spot spot = (Spot) star.getTag(); 
						
						Toast.makeText(ParkingSpotDetailActivity.this,"You clicked a star for spot " + spot.getId(),Toast.LENGTH_LONG).show();
						
					    ReserveSpot((Integer) spot.getId());
					    
					    // Update star
					    star.setVisibility(star.INVISIBLE);
						
					    //Navigate To the spot...
						navigate(spot.getLat(), spot.getLongitude());

					}
					  
				  });
						  
			  }
		  }
			    
	 }
		  
	  public void ReserveSpot(int spotID )
	  {
		  String SpotID = Integer.toString(spotID);
		  String User = Session.getInstance().getUser().getEmail();
		  String Password = Session.getInstance().getUser().getPassword();
		  RestTaskFactory.makeReservation(this,User,Password, SpotID,"RESERVED");
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
		 boolean result = new MainMenu(this).handleOnClick(item);
			invalidateOptionsMenu();
			return result;
	 }
	  
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  if(Session.getInstance().getUser() != null)
	    		getMenuInflater().inflate(R.menu.main_logged_in, menu);
	    	else
	    		getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	  }
	  
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 invalidateOptionsMenu();
		 super.onActivityResult(requestCode, resultCode, data);
	  }
	  
	  private void navigate(double Latitude, double Longitude)
	  {
		  LocationManager sensorManager = ((LocationManager)getSystemService(Context.LOCATION_SERVICE));
		  CurrentLocation = sensorManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		  
		  // Set up the location Listener...
		  LocationListener locationListener = new LocationListener() {
			     public void onLocationChanged(Location location) {
			    	 CurrentLocation= location;
				  }

				  public void onProviderDisabled(String provider){}
				  public void onProviderEnabled(String provider) {}
				  public void onStatusChanged(String provider, int status, Bundle extras) {}
		  };
		  
		  sensorManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener );
		  
		  // Wait till you get a location..
		  while( CurrentLocation == null )
		  {
			  CurrentLocation = sensorManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		  }
		  
		  String googleMapsIntent = "http://maps.google.com/maps?saddr=" + CurrentLocation.getLatitude() + "," + CurrentLocation.getLongitude() + "&daddr=" + Latitude + "," + Longitude;
		  Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsIntent));
		  startActivityForResult(i, 1);
	  }
	  
}


