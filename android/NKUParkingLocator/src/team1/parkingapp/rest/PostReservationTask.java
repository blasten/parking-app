package team1.parkingapp.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import team1.parkingapp.data.Reservation;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PostReservationTask extends AsyncTask<String, Void, Reservation> {
	private Context ctx;				// Context on which to show the ProgressDialog
	private ProgressDialog progress;	// Lets the user know that the app is working
	
	/*
	 * Just assign the context reference.
	 */
	protected PostReservationTask(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	/*
	 * Display a ProgressDialog to the user.
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress = new ProgressDialog(ctx);
		progress.setTitle("Creating Reservation");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
	}
	
	/*
	 * Make the POST request to create a reservation.
	 */
	@Override
	protected Reservation doInBackground(String... params) {
		Reservation res = null;											// Reservation being placed
		String email = params[0];										// Email of the user placing the res
		String pwd = params[1];											// Password of the user placing the res
		String spot = params[2];										// Spot ID being reserved
		String resStatus  = params[3];										// Status of the spot (reserved)
		HttpClient client = new DefaultHttpClient();					// HTTP client to perform the request
		HttpPost httpPost = new HttpPost(RestContract.RESERVATIONS_API);// HTTP POST header
	    UsernamePasswordCredentials creds = 							// User's credentials 
	    		new UsernamePasswordCredentials(email, pwd);
		
	    // Add those credentials brah
	 	((AbstractHttpClient) client).getCredentialsProvider()
	 		.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
		try {
			// Add the parameters to the request
			List<NameValuePair> args = new ArrayList<NameValuePair>(2);
			
			args.add(new BasicNameValuePair(RestContract.RESERVATION_SPOT_ID, spot));
			args.add(new BasicNameValuePair(RestContract.RESERVATION_STATUS, resStatus));
			
			// Execute the request and get the response
			httpPost.setEntity(new UrlEncodedFormEntity(args));
			HttpResponse response = client.execute(httpPost);
			StatusLine status = response.getStatusLine();
			
			if (status.getStatusCode() == HttpStatus.SC_CREATED) {
				Log.i("POST Reservation", Integer.toString(status.getStatusCode()));
				Log.i("POST Reservation", status.getReasonPhrase());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				res = this.parseResults(out.toString());
				out.close();
			}
		}
		catch (Exception e) {
			Log.e("POST Reservation", e.getMessage());
			return null;
		}
		
		return res;
	}
	
	/*
	 * Dismiss the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(Reservation results) {
		super.onPostExecute(results);
		progress.dismiss();
	}
	
	/*
	 * Takes a string containing JSON data, parses it out, and returns a Reservation object.
	 */
	private Reservation parseResults(String results) {
		Reservation res = null;
		
		JSONTokener tokener = new JSONTokener(results);
		try {
			JSONObject json = new JSONObject(tokener);
			res = Reservation.validateJSONData(json);
			Session.getInstance().setReservation(res);
		}
		catch (Exception e) {
			Log.e("POST Reservation", "Error parsing JSON objects");
			return null;
		}
		
		return res;
	}

}
