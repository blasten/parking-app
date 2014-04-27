package team1.parkingapp.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParkingLot
{
	
	private int id;
	private String name;
	private boolean enabled;
	private long spotsAvailable;
	private double lat;
	private double lng;
	private MarkerOptions markerOptions;
	private LatLng latlng;
	private String role;
	
	public ParkingLot(int id, String name, boolean enabled, long spotsAvailable, double lat, double lng, String role)
	{
		this.id = id;
		this.name = name;
		this.enabled = enabled;
		this.spotsAvailable = spotsAvailable;
		this.lat = lat;
		this.lng = lng;
		this.latlng = new LatLng(this.lat, this.lng);
		this.markerOptions = new MarkerOptions().position(this.latlng).title(this.name);
		this.role = role;
	}

	public String getRole()
	{
		return this.role;
	}
	
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