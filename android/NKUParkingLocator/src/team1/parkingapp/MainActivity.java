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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	  static final LatLng NKU = new LatLng(39.031087, -84.466808);
	  static final LatLng AIM_SURPLUS = new LatLng(39.490509, -84.366419);
	  private GoogleMap map;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_main);
		    
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    		.getMap();
		    
		    Marker nku = map.addMarker(new MarkerOptions().position(NKU)
		        .title("NKU"));
		    Marker aim = map.addMarker(new MarkerOptions()
		        .position(AIM_SURPLUS)
		        .title("AIM Surplus")
		        .snippet("Stop taking my money")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));
			
		    // Move the camera instantly to NKU with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKU, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  // Inflate the menu; this adds items to the action bar if it is present.
		  getMenuInflater().inflate(R.menu.main, menu);
		  return true;
	  }
}