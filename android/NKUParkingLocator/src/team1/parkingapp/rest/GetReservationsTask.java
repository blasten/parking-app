/*
 * GetReservationsTask.java
 * 4/22/14
 * Travis Carney
 * 
 * This class contains the implementation for the GET reservations request.
 * You can request all of the reservations a user has, or get information
 * about an individual reservation.
 */
package team1.parkingapp.rest;

import java.util.Vector;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import team1.parkingapp.data.Reservation;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetReservationsTask extends AsyncTask<String, Void, Vector<Reservation>> {
	private Context ctx;				// Context on which to show the ProgressDialog
	private ProgressDialog progress;		// Let's the user know the app is working
	
	/*
	 * Just assign the context reference.
	 */
	protected GetReservationsTask(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	/*
	 * Setup the ProgressDialog and then show it.
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress = new ProgressDialog(ctx);
		progress.setTitle("Getting Reservations");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
	}
	
	/*
	 * Make the GET request to get the user's reservations.
	 */
	@Override
	protected Vector<Reservation> doInBackground(String... params) {
		String email = params[0];										// User's email
		String pwd = params[1];											// User's password
		String args = "";												// GET parameters (resId)
		Integer resId = null;											// Reservation ID user is requesting
		Vector<Reservation> reservations = new Vector<Reservation>();	// User's reservations
		HttpClient client = new DefaultHttpClient();					// HTTP client to perform the GET request
	    UsernamePasswordCredentials creds = 							// User's credentials 
	    		new UsernamePasswordCredentials(email, pwd);
		
		if (params[2] != null)	// See if the user is trying to get details about a particular spot
			resId = Integer.parseInt(params[2]);
		
		// Add those credentials brah
		((AbstractHttpClient) client).getCredentialsProvider()
			.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
		try {

			if (resId != null)
				args = resId.toString();
			
			HttpGet httpGet = new HttpGet(RestContract.RESERVATIONS_API + args);	// HTTP GET header
			HttpResponse response = client.execute(httpGet);						// Response from the server
			StatusLine status = response.getStatusLine();
			
			// Read the response and create the reservations vector
			if (status.getStatusCode() == HttpStatus.SC_OK) {
				Log.i("GET Reservation", Integer.toString(status.getStatusCode()));
				Log.i("GET Reservation", status.getReasonPhrase());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				reservations = this.parseResults(out.toString());
				out.close();
			}
		}
		catch (Exception e) {
			Log.e("GET Reservations", e.getMessage());
			return null;
		}
		
		return reservations;
	}
	
	/*
	 * Dismiss the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(Vector<Reservation> results) {
		super.onPostExecute(results);
		progress.dismiss();
	}
	
	/*
	 * Takes a string containing JSON data, parses it out, and returns a vector of reservations.
	 */
	private Vector<Reservation> parseResults(String results) {
		Vector<Reservation> reservations = new Vector<Reservation>();
		
		JSONTokener tokener = new JSONTokener(results);
		try {
			JSONArray arr = new JSONArray(tokener);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				reservations.add(Reservation.validateJSONData(json));
			}
		}
		catch (Exception e) {
			Log.e("GET Reservations", e.getMessage());
			return null;
		}
		
		return reservations;
	}
}
