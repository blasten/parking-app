package team1.parkingapp.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParkingLot
{
	private int id;							//Lot ID
	private String name;					//Name of lot
	private boolean enabled;				//Whatever enabled is
	private long spotsAvailable;			//Number of spots that are available
	private double lat;						//Latitude of center of parking lot
	private double lng;						//Longitude of center of parking lot
	private MarkerOptions markerOptions;	//Marker Options for placing lot on map
	private LatLng latlng;					//LatLng object that holds that lat and Long - for use in map
	
	public ParkingLot(int id, String name, boolean enabled, long spotsAvailable, double lat, double lng)
	{
		this.id = id;
		this.name = name;
		this.enabled = enabled;
		this.spotsAvailable = spotsAvailable;
		this.lat = lat;
		this.lng = lng;
		this.latlng = new LatLng(this.lat, this.lng);
		this.markerOptions = new MarkerOptions().position(this.latlng).title(this.name);
	}

	//Only Accessors are below.  Mutators are not included because mobile users cannot modify parking lots
	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

	public long getSpotsAvailable()
	{
		return this.spotsAvailable;
	}
	
	public double getLat()
	{
		return this.lat;
	}
	
	public double getLng()
	{
		return this.lng;
	}
	
	public LatLng getLatLng()
	{
		return this.latlng;
	}
	
	public MarkerOptions getMarkerOptions()
	{
		return this.markerOptions;
	}
	
	
	//Override toString() to give useful information when printed.
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("ID: \t\t\t\t" + this.getId());
		sb.append("\n");
		
		sb.append("NAME:\t\t\t\t" + this.getName());
		sb.append("\n");
		
		sb.append("ENABLED:\t\t\t" + this.isEnabled());
		sb.append("\n");
		
		sb.append("AVAILABLE SPOTS:\t" + this.getSpotsAvailable());
		sb.append("\n");
		
		sb.append("LATITUDE:\t\t\t" + this.getLat());
		sb.append("\n");
		
		sb.append("LONGITUDE:\t\t\t" + this.getLng());
		sb.append("\n");
		
		return sb.toString();
	}
}