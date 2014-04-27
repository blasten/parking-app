/*
 * RestTaskFactory.java
 * 4/6/14
 * Travis Carney
 * 
 * This class is used to create REST tasks for our REST API. The various requests are:
 * 
 * GET users/USER_NAME - Returns data about a user.
 * PUT users/USER_NAME - Modifies a user.
 * POST users - Creates a mobile user.
 * GET spots - Gives a list of spots in an area.
 * GET spots/id - Get information about a specific spot.
 * POST reserve-spot - Reserves an available spot.
 * DELETE reserve-spot - Deletes a reservation.
 * POST checkin - Check in a spot.
 * POST checkout - Check out from a spot.
 * GET my-spots - Returns a list of spots the user has used.
 */
package team1.parkingapp.rest;

import java.util.Vector;
import java.util.concurrent.ExecutionException;

import team1.parkingapp.data.Spot;

import android.content.Context;
import android.util.Log;

public class RestTaskFactory {

	/*
	 * Creates a new user with the given email, password, first name, and lastname.
	 * Returns the PostUserTask object that was created. This task assumes that UserRegistrationActivity is calling it.
	 */
	public static PostUserTask createNewUser(Context ctx, String email, String password, String firstName, String lastName) {
		return (PostUserTask) new PostUserTask(ctx).execute(email, password, firstName, lastName);
	}
	
	public static PostReservationTask makeReservation(Context ctx, String email, String password, String Spot, String status) {
		return (PostReservationTask) new PostReservationTask(ctx).execute(email, password, Spot, status);
	}
	
	/*
	 * Gets a spot based on its ID.
	 */
	public static GetSpotsTask getSpotsById(Context ctx, int spotId) {
		return (GetSpotsTask) new GetSpotsTask(ctx).execute(Integer.toString(spotId));
	}
	
	/*
	 * Get spot(s) based on their latitude and longitude coordinates.
	 */
	public static GetSpotsTask getSpotsByCoords(Context ctx, String lat1, String long1, String lat2, String long2) {
		return (GetSpotsTask) new GetSpotsTask(ctx).execute(lat1, long1, lat2, long2);
	}
	
	
	/*
	 * Gets all spots that are in the lot specified by lotId.
	 */
	public static Vector<Spot> getSpotsByLot(Context ctx, int lotId) {
		try
		{
			return ((GetSpotsTask) new GetSpotsTask(ctx)).execute(Integer.toString(lotId), "extra").get();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/*
	 * Get all parking lots
	 */
	public static GetLotTask getParkingLots(Context ctx, String role) {
		return (GetLotTask) new GetLotTask(ctx).execute(role);
	}
	
	/*
	 * Updates a user's information. If you don't want to change one or more of the fields for a user,
	 * pass in null for the field(s) you don't want to change. For example if you only want to change the
	 * user's password, pass in null for newEmail, newName, and newLastname.
	 */
	public static PutUserTask putUser(Context ctx, String email, String pwd, String userId, String newEmail, String newPwd, 
			String newName, String newLastname) {
		return (PutUserTask) new PutUserTask(ctx).execute(email, pwd, userId, newEmail, newPwd, newName, newLastname);
	}
	
	/*
	 * Gets all reservations a user has made.
	 */
	public static GetReservationsTask getAllReservations(Context ctx, String email, String password) {
		return (GetReservationsTask) new GetReservationsTask(ctx).execute(email, password, null);
	}
	
	/*
	 * Gets information about a particular reservation a user has made. The resId parameter is the id of
	 * the reservation you want to see.
	 */
	public static GetReservationsTask getReservationById(Context ctx, String email, String password, String resId) {
		return (GetReservationsTask) new GetReservationsTask(ctx).execute(email, password, resId);
	}
	
	public static PutReservationTask changeReservation(Context ctx, int reservationID, int spotID, String status, String username, String password)
	{
		return (PutReservationTask) new PutReservationTask(ctx).execute(Integer.toString(reservationID), Integer.toString(spotID),status, username, password);
	}
	
	public static GetUserTask getUser(Context ctx, String username, String password)
	{
		return (GetUserTask) new GetUserTask(ctx).execute(username, password);
	}
	
	public static DeleteReservationTask deleteReservation(Context ctx, String username, String password, int spotId)
	{
		return (DeleteReservationTask) new DeleteReservationTask(ctx).execute(Integer.toString(spotId),username, password);
	}
	
}