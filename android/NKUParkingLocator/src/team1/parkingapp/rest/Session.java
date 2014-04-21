package team1.parkingapp.rest;

import java.util.Vector;

import team1.parkingapp.data.ParkingLot;
import team1.parkingapp.data.Reservation;
import team1.parkingapp.data.User;

public class Session {

	private static User u = null;
	//private static Reservation r;
	private static Vector < ParkingLot > parkingLots = null;
	private static Reservation curR = null;
	
	private Session() {}
	
	public static User getUser()
	{
		return u;
	}
	
	protected static void setUser(User u)
	{
		Session.u = u;
	}
	
	public static Vector < ParkingLot > getParkingLots()
	{
		return parkingLots;
	}
	
	protected static void setParkingLots(Vector < ParkingLot > v)
	{
		Session.parkingLots = v;
	}
	
	public static Reservation getReservation()
	{
		return Session.curR;
	}
	
	protected static void setReservation(Reservation reservation)
	{
		Session.curR = reservation;
	}
	
}
