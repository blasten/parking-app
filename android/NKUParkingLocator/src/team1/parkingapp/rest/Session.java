/*
 * Session.java
 * 4/22/14
 * Mat Ferry
 * 
 * This is a Singleton that tracks the current user, their reservation, and parking lots that are available.
 */
package team1.parkingapp.rest;

import java.util.Vector;

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Reservation;
import team1.parkingapp.data.User;

public final class Session {

	private User u = null;			
	private Vector < ParkingLot > parkingLots = null;
	private Reservation curR = null;
	
	private static Session instance = null;
	
	private Session() {}
	
	public static Session getInstance()
	{
		if(instance == null)
			instance = new Session();
		return instance;
	}
	
	public User getUser()
	{
		return u;
	}
	
	public void setUser(User u)
	{
		this.u = u;
	}
	
	public Vector < ParkingLot > getParkingLots()
	{
		return parkingLots;
	}
	
	protected void setParkingLots(Vector < ParkingLot > v)
	{
		this.parkingLots = v;
	}
	
	public Reservation getReservation()
	{
		return this.curR;
	}
	
	protected void setReservation(Reservation reservation)
	{
		this.curR = reservation;
	}
	
}
