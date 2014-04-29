/*
 * SpotList.java
 * 4/28/14
 * Travis Carney
 * 
 * This activity shows a list of spots and allows the user to make reservations.
 */
package team1.parkingapp;

import java.util.Vector;
import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Spot;
import team1.parkingapp.rest.RestContract;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.ListActivity;
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
import android.widget.ListView;
import android.widget.Toast;

public class SpotList extends ListActivity {
	private Vector<Spot> spots;			// Vector of the spots in the current lot
	private Location CurrentLocation; 	// Where the dumbass using this app currently stands
	
	/*
	 * Gets the current lot's spots and sets up the list of spots.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //ParkingLot currentParkingLot = null;
        
        // Get the title of the current lot
	    String TitleScreen = getIntent().getExtras().getString("GarageTitle");
        int lotID = getLotID(TitleScreen);
		
        // Get the spots
        spots = RestTaskFactory.getSpotsByLot(this, lotID);
		
        // Setup the SpotAdapter to display the spots.
		SpotAdapter adapter = new SpotAdapter(this, R.layout.spot_row, spots);
		setListAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Session.getInstance().getReservation() != null && Session.getInstance().getReservation().getStatus().equals(RestContract.RESERVED))
			getMenuInflater().inflate(R.menu.main_has_reservation, menu);
		else if(Session.getInstance().getReservation() != null && Session.getInstance().getReservation().getStatus().equals(RestContract.OCCUPIED))
			getMenuInflater().inflate(R.menu.main_is_checked_in, menu);
		else if(Session.getInstance().getUser() != null)
			getMenuInflater().inflate(R.menu.main_logged_in, menu);
		else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * If the user doesn't have a reservation, let them reserve a spot.
	 * Otherwise tell them where they can shove it.
	 */
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		Spot currentSpot = spots.get(pos);
		
		// Don't let dicks conquer the parking lot
		if (Session.getInstance().getReservation() != null) {
			Toast.makeText(this, R.string.user_already_has_reservation, Toast.LENGTH_LONG).show();
			return;
		}
		
		if ( !currentSpot.getStatus().equals(RestContract.AVAILABLE) ) {
			Toast.makeText(this, R.string.spot_unavailable, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// If the user isn't a dick, let them reserve the spot and go there
		reserveSpot(currentSpot.getId());
		navigate(currentSpot.getLat(), currentSpot.getLongitude());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = new MainMenu(this).handleOnClick(item);
		invalidateOptionsMenu();
		return result;
	}
	
	/*
	 * Get the lot ID of the current lot based on its title.
	 */
	public int getLotID(String strTitle) {
		int intLotID = 0;
	  	Vector<ParkingLot> lots = Session.getInstance().getParkingLots();
	  	
		for (int i = 0 ; i < lots.size() ; ++i) {
			if(strTitle.equals(lots.get(i).getName())) {
				intLotID = lots.get(i).getId();
    		}
		}
		
		return intLotID;
	}
	
	/*
	 * Reserves a spot based on its ID.
	 */
	private void reserveSpot(int spotID ) {
		String SpotID = Integer.toString(spotID);
		String User = Session.getInstance().getUser().getEmail();
		String Password = Session.getInstance().getUser().getPassword();
		RestTaskFactory.makeReservation(this,User,Password, SpotID,"RESERVED");
	}
	
	/*
	 * Performs navigation to the given coordinates.
	 */
	private void navigate(double Latitude, double Longitude) {
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
	  	while( CurrentLocation == null ) {
	  		CurrentLocation = sensorManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	  	}
		  
	  	String googleMapsIntent = "http://maps.google.com/maps?saddr=" + CurrentLocation.getLatitude() + "," + CurrentLocation.getLongitude() + "&daddr=" + Latitude + "," + Longitude;
	  	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsIntent));
	  	startActivityForResult(i, 1);
	  	finish();
	}	
}
