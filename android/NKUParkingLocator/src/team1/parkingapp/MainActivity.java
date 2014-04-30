/*
 * MainActivity.java
 * 2/26/14
 * Travis Carney, Mat Ferry, Todd Phillips
 * 
 * This file contains the main activity for the NKU Parking Locator app.
 */
package team1.parkingapp;

import java.util.Vector;

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.rest.RestContract;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends Activity implements  OnInfoWindowClickListener  {
	  private static final LatLng NKU = new LatLng(39.031087, -84.466808);
	  private static final int NUMBER_OF_PARKING_LOT_PICTURES = 7;
	  private Vector<ParkingLot> lots;
	  private GoogleMap map;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_main);
		    
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    		.getMap();
		    
		    try
		    {
		    	RestTaskFactory.getParkingLots(this, Session.getInstance().getUser().getRole()).get();
		    }
		    catch(Exception e)
		    {
		    	new AlertDialog.Builder(this)
					.setTitle("Parking App")
					.setMessage("Could not connect to the network.  Application will now close.\nPlease try again.")
					.setNeutralButton("OK", null)
					.show();
		    	finish();
		    }
		    lots = Session.getInstance().getParkingLots();
		    for(int i = 0 ; i < lots.size() ; ++i)
		    {
		    	map.addMarker(lots.get(i).getMarkerOptions());
		    }
			
		    
		    // Move the camera instantly to NKU with a zoom of 16.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKU, 16));
		    
		    // Set the custom info screen listener 
		    map.setOnInfoWindowClickListener(this);
		    
		    // Custom Marker Window... 
		    map.setInfoWindowAdapter(new InfoWindowAdapter() {

		    	// Do nothing here.. 
		        @Override
		        public View getInfoWindow(Marker arg0) {
		            return null;
		        }

		        @Override
		        public View getInfoContents(Marker arg0) {
		        	View v = getLayoutInflater().inflate(R.layout.customlayout, null);
		        	TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
		        	TextView txtAvailableSpot = (TextView) v.findViewById(R.id.txtAvailableSpot);
		    
		        	// Set the title
		        	txtTitle.setText(arg0.getTitle());
		        	
		        	// Set the Image
		        	ImageView image = (ImageView) v.findViewById(R.id.parkinglotphoto);
		        	for(int i = 0 ; i < lots.size() ; ++i)
		        	{
		        		if(arg0.getTitle().equals(lots.get(i).getName()))
		        		{
		        			// If we have a picture for it use it.
		        			if( i < NUMBER_OF_PARKING_LOT_PICTURES ) image.setImageResource(  getResources().getIdentifier("parkinglot" + (i + 1) , "drawable", "team1.parkingapp"));
		        			//If not get a random one
		        			else image.setImageResource( getRandomDrawable());
		        			
		        			// Set the number of available spots
		        			txtAvailableSpot.append(Long.toString(lots.get(i).getSpotsAvailable()));
		        		}
		        	}
		        	
		            return v;
		        }
		    });
	  }

	  @Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
		
	}
	  
	  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	  
		public boolean onOptionsItemSelected(MenuItem item)
		{
			boolean result = new MainMenu(this).handleOnClick(item);
			invalidateOptionsMenu();
			return result;
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
	  
	  private int getRandomDrawable()
	  {
		  int n = (int)((Math.random()) * NUMBER_OF_PARKING_LOT_PICTURES) + 1;
		  int res = getResources().getIdentifier("parkinglot" + n, "drawable", "team1.parkingapp");
		  return res;
	  }

	 @Override
	 public void onInfoWindowClick(Marker arg0) {
		 //Intent i = new Intent(this, team1.parkingapp.ParkingSpotDetailActivity.class);
		 Intent i = new Intent(this, team1.parkingapp.SpotList.class);
		 i.putExtra("GarageTitle",arg0.getTitle());
		
		 startActivityForResult(i, 1);
		 invalidateOptionsMenu();
	 }
		
	  
	  @Override
	  public void onRestart() { 
		  
		  	super.onRestart();
		  	
			// Clear the map and reload everything updated when the back button is pressed. 
			map.clear();
			
		    try
		    {
		    	RestTaskFactory.getParkingLots(this, Session.getInstance().getUser().getRole()).get();
		    }
		    catch(Exception e)
		    {
		    	new AlertDialog.Builder(this)
					.setTitle("Parking App")
					.setMessage("Could not connect to the network.  Application will now close.\nPlease try again.")
					.setNeutralButton("OK", null)
					.show();
		    	finish();
		    }
		    // Refresh lot information to display possible reserved lot..
		    lots = Session.getInstance().getParkingLots();
		    
		    for(int i = 0 ; i < lots.size() ; ++i)
		    {
		    	map.addMarker(lots.get(i).getMarkerOptions());
		    }
			
		    
		    // Move the camera instantly to NKU with a zoom of 16.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKU, 16));
		    
		    // Set the custom info screen listener 
		    map.setOnInfoWindowClickListener(this);
		    
		    // Custom reset Marker Window... 
		    map.setInfoWindowAdapter(new InfoWindowAdapter() {
	
		    	// Do nothing here.. 
		        @Override
		        public View getInfoWindow(Marker arg0) {
		            return null;
		        }
	
		        @Override
		        public View getInfoContents(Marker arg0) {
		        	View v = getLayoutInflater().inflate(R.layout.customlayout, null);
		        	TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
		        	TextView txtAvailableSpot = (TextView) v.findViewById(R.id.txtAvailableSpot);
		    
		        	// Set the title
		        	txtTitle.setText(arg0.getTitle());
		        	
		        	// Set the Image
		        	ImageView image = (ImageView) v.findViewById(R.id.parkinglotphoto);
		        	for(int i = 0 ; i < lots.size() ; ++i)
		        	{
		        		if(arg0.getTitle().equals(lots.get(i).getName()))
		        		{
		        			// If we have a picture for it use it.
		        			if( i < NUMBER_OF_PARKING_LOT_PICTURES ) image.setImageResource(  getResources().getIdentifier("parkinglot" + (i + 1) , "drawable", "team1.parkingapp"));
		        			// If not get a random one
		        			else image.setImageResource( getRandomDrawable());
		        			
		        			// Set the number of available spots
		        			txtAvailableSpot.append(Long.toString(lots.get(i).getSpotsAvailable()));
		        		}
		        	}
		        	
		            return v;
		        }
		    });
	  }
	  
	  /*
	   * When the user presses the back button, it should log them out.
	   */
	  @Override
	  public void onBackPressed() {
		  Session.getInstance().setUser(null);
		  this.finish();
	  }
}
