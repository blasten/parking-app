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
	  
	  /*static final LatLng GarageOne = new LatLng(39.032266, -84.461506);
	  static final LatLng GarageTwo = new LatLng(39.034431, -84.464019);
	  static final LatLng GarageThree = new LatLng(39.032689, -84.468182);
	  static final LatLng GarageFour = new LatLng(39.031514, -84.468311);
	  static final LatLng GarageFive = new LatLng(39.030247, -84.467656);
	  static final LatLng GarageSix = new LatLng(39.030064, -84.461187);
	  static final LatLng GarageSeven = new LatLng(39.028388, -84.466455);
	  static final LatLng AIM_SURPLUS = new LatLng(39.490509, -84.366419); 
	  static final LatLng AIM_SURPLUS = new LatLng(39.490509, -84.366419);*/ 
	  private GoogleMap map;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) { 
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_main);
		    
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    		.getMap();
		    
		    try
		    {
		    	RestTaskFactory.getParkingLots(this).get();
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
		        	 
		        	ImageView image = (ImageView) v.findViewById(R.id.parkinglotphoto);
		        	for(int i = 0 ; i < lots.size() ; ++i)
		        	{
		        		if(arg0.getTitle().equals(lots.get(i).getName()))
		        		{
		        			// If we have a picture for it use it.
		        			if( i < NUMBER_OF_PARKING_LOT_PICTURES ) image.setImageResource(  getResources().getIdentifier("parkinglot" + (i + 1) , "drawable", "team1.parkingapp"));
		        			//If not get a random one
		        			else image.setImageResource( getRandomDrawable());
		        		}
		        	}
		            return v;

		        }
		    });

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
	  
	  private int getRandomDrawable()
	  {
		  int n = (int)((Math.random()) * NUMBER_OF_PARKING_LOT_PICTURES) + 1;
		  int res = getResources().getIdentifier("parkinglot" + n, "drawable", "team1.parkingapp");
		  return res;
	  }

		@Override
		public void onInfoWindowClick(Marker arg0) {
			Intent i = new Intent(this, team1.parkingapp.ParkingSpotDetailActivity.class);
			i.putExtra("GarageTitle",arg0.getTitle());
			startActivity(i);
			
		}
}
