/*
 * MainActivity.java
 * 2/26/14
 * Travis Carney, Mat Ferry, Todd Phillips
 * 
 * This file contains the main activity for the NKU Parking Locator app.
 * Right now it is just a test bed for the map that has two markers.
 * For reference, this tutorial was used: http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html#overview_intro
 */
package team1.parkingapp;

import android.os.Bundle;
import android.app.Activity; 
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	  static final LatLng NKU = new LatLng(39.031087, -84.466808);
	  static final LatLng GarageOne = new LatLng(39.032266, -84.461506);
	  static final LatLng GarageTwo = new LatLng(39.034431, -84.464019);
	  static final LatLng GarageThree = new LatLng(39.032689, -84.468182);
	  static final LatLng GarageFour = new LatLng(39.031514, -84.468311);
	  static final LatLng GarageFive = new LatLng(39.030247, -84.467656);
	  static final LatLng GarageSix = new LatLng(39.030064, -84.461187);
	  static final LatLng GarageSeven = new LatLng(39.028388, -84.466455);
	  static final LatLng AIM_SURPLUS = new LatLng(39.490509, -84.366419); 
	  private GoogleMap map;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) { 
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_main);
		    
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    		.getMap();
		    
		    Marker G1 = map.addMarker(new MarkerOptions().position(GarageOne)
			        .title("Garage 1").snippet("Number Of Avalable Spots: 32 \n Number of Total Spots: 52 \n ") );
		    Marker G2 = map.addMarker(new MarkerOptions().position(GarageTwo)
			        .title("Garage 2"));
		    Marker G3 = map.addMarker(new MarkerOptions().position(GarageThree)
			        .title("Garage 3"));
		    Marker G4 = map.addMarker(new MarkerOptions().position(GarageFour)
			        .title("Garage 4"));
		    Marker G5 = map.addMarker(new MarkerOptions().position(GarageFive)
			        .title("Garage 5"));
		    Marker G6 = map.addMarker(new MarkerOptions().position(GarageSix)
			        .title("Garage 6"));
		    Marker G7 = map.addMarker(new MarkerOptions().position(GarageSeven)
			        .title("Garage 7"));
		    
		    Marker aim = map.addMarker(new MarkerOptions()
		        .position(AIM_SURPLUS)
		        .title("AIM Surplus")
		        .snippet("Stop taking my money")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));
			
		    // Move the camera instantly to NKU with a zoom of 16.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKU, 16));
		    
		    // Custom Marker Window... 
		    map.setInfoWindowAdapter(new InfoWindowAdapter() {

		        @Override
		        public View getInfoWindow(Marker arg0) {
		            return null;
		        }

		        @Override
		        public View getInfoContents(Marker arg0) {
		        	//if(arg0.equals(G1) ){; }
		            View v = getLayoutInflater().inflate(R.layout.customlayoutg1, null);
		            return v;

		        }
		    });

	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  // Inflate the menu; this adds items to the action bar if it is present.
		  getMenuInflater().inflate(R.menu.main, menu);
		  return true;
	  }
}