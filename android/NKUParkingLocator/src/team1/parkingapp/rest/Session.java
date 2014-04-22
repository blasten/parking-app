package team1.parkingapp.rest;

import java.util.Vector;

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Reservation;
import team1.parkingapp.data.User;

public final class Session {

	private User u = null;
	//private static Reservation r;
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
	
	protected void setUser(User u)
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
