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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import team1.parkingapp.data.Reservation;
import team1.parkingapp.data.User;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PutReservationTask  extends AsyncTask<String, String, String> {
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog
	private String[] postKeys =			// Array of keys to be sent to the server
    	{RestContract.RESERVATION_SPOT_ID, RestContract.RESERVATION_STATUS};

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	
	public PutReservationTask(Context ctx)
	{
		super();
		this.ctx = ctx;
	}
	
	/*
	 * Setup the ProgressDialog and then show it.
	 */
	@Override
	protected void onPreExecute()
	{
		progress = new ProgressDialog(ctx);
		progress.setTitle("Retrieving Parking Information");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
		super.onPreExecute();
	}
	
	/*
	 * Make the POST request and then get the response.
	 */
	@Override
	protected String doInBackground(String... params)
	{
		    // Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();

		    try {
		    	
		    	UsernamePasswordCredentials creds = new UsernamePasswordCredentials(params[3], params[4]);
		        ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
		    	
		    	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postKeys.length);
			        
		        // Add the data to the request
		        for (int i = 0; i < postKeys.length; i++) 
		        	nameValuePairs.add(new BasicNameValuePair(postKeys[i], params[i + 1]));
		    	
		    	HttpPut httpput = new HttpPut(RestContract.RESERVATIONS_API + params[0]);
		        httpput.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    	
		        // Execute HTTP GET request & get the response
		        HttpResponse response = httpclient.execute(httpput);
		        
		        StatusLine status = response.getStatusLine();
		        
		        if(status.getStatusCode() != HttpStatus.SC_OK)
		        {
		        	throw new Exception();
		        }
		        else
		        {
		        	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                String s = out.toString();
	                out.close();
	                if(s.toLowerCase().contains("error"))
	                {
	                	Session.getInstance().setReservation(null);
	                }
	                else
	                {
	                	Session.getInstance().setReservation(parseResults(s));
	                }
	                return s;
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("GET Lot", e.getMessage());
		    	return null;
		    }
	}
	
	private Reservation parseResults(String results)
	{
		Reservation reservation = null;
		
		Log.i("PUT User Resuls", results);
		
		JSONTokener tokener = new JSONTokener(results);
		try {
			JSONObject json = new JSONObject(tokener);
			reservation = Reservation.validateJSONData(json);
		}
		catch (Exception e) {
			Log.e("PUT User", "Error parsing JSON objects");
			return null;
		}
		
		return reservation;
	}
	
	/*
	 * Close the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(String result)
	{
		progress.dismiss();
		super.onPostExecute(result);
	}
}