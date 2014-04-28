package team1.parkingapp.data;

import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ParkingSpotView extends View implements OnClickListener
{

	private float topLeftX;
	private float topLeftY;
	private float botRightX;
	private float botRightY;
	private boolean isAvail;
	private double Latitude;
	private double Longitude;
	private Context ctx;
	private int spotID;
	private Activity parent;
	private Location CurrentLocation;
	private Paint linePaint;
	private Paint circlePaint;
	private float radius;
	
	public ParkingSpotView(Context ctx, float topLeftX, float topLeftY, float botRightX, float botRightY,
			float radius, boolean isAvail, double Latitude, double Longitude, int spotID, Activity parent)
	{
		super(ctx);
		this.ctx = ctx;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.botRightX = botRightX;
		this.botRightY = botRightY;
		this.isAvail = isAvail;
		this.Latitude = Latitude;
		this.Longitude = Longitude;
		this.spotID = spotID;
		this.parent = parent;
		this.radius = radius;
		linePaint = new Paint();
		linePaint.setColor(Color.BLACK);
		circlePaint = new Paint();
		circlePaint.setColor(Color.GREEN);
	}

	@Override
	public void onClick(View v) {
		if(isAvail)
		{
			String SpotID = Integer.toString(spotID);
			String User = Session.getInstance().getUser().getEmail();
			String Password = Session.getInstance().getUser().getPassword();
			RestTaskFactory.makeReservation(ctx,User,Password, SpotID,"RESERVED");
			navigate(Latitude, Longitude);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawLine(topLeftX, topLeftY, botRightX, topLeftY, linePaint);
		canvas.drawLine(topLeftX, topLeftY, topLeftX, botRightY, linePaint);
		canvas.drawLine(botRightX, topLeftY, botRightX, botRightY, linePaint);
		canvas.drawLine(topLeftX, botRightY, botRightX, botRightY, linePaint);
		if(isAvail)
			canvas.drawCircle( (topLeftX + botRightX) / 2, (topLeftY + botRightY) / 2, radius, circlePaint);
		
	}
	
	private void navigate(double Latitude, double Longitude)
	{
		LocationManager sensorManager = ((LocationManager)parent.getSystemService(Context.LOCATION_SERVICE));
		CurrentLocation = sensorManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//Set up the location Listener...
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				CurrentLocation = location;
			}
			
			public void onProviderDisabled(String provider){}
			public void onProviderEnabled(String provider) {}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};
		sensorManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener );

		//Wait till you get a location..
		while( CurrentLocation == null )
		{
			CurrentLocation = sensorManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		String googleMapsIntent = "http://maps.google.com/maps?saddr=" + CurrentLocation.getLatitude() + "," + CurrentLocation.getLongitude() + "&daddr=" + Latitude + "," + Longitude;
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsIntent));
		parent.startActivityForResult(i, 1);
	}
	
	
	
}
