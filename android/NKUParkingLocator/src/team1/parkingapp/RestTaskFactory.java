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
package team1.parkingapp;

import android.content.Context;

public class RestTaskFactory {
	protected static final String BASE_API_URL = "http://parking-app.herokuapp.com/api/";

	/*
	 * Creates a new user with the given email, password, first name, and lastname.
	 * Returns the PostUserTask object that was created.
	 */
	public static PostUserTask createNewUser(Context ctx, String email, String password, String firstName, String lastName) {
		return (PostUserTask) new PostUserTask(ctx).execute(email, password, firstName, lastName);
	}
}
