/*
 * GetSpotsTask.java
 * 4/16/14
 * Travis Carney
 * 
 * This class contains the implementation for the GET spots request.
 */
package team1.parkingapp.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Vector;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import team1.parkingapp.data.Spot;

public class GetSpotsTask extends AsyncTask<String, Void, Vector<Spot> > {
	private ProgressDialog progress;	// ProgressDialog letting the user know the app is working on their request
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Assign the context field.
	 */
	protected GetSpotsTask(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	/*
	 * Setup a ProgressDialog and show it.
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress = new ProgressDialog(this.ctx);
		progress.setTitle("Getting parking spots");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
	}
	
	@Override
	protected Vector<Spot> doInBackground(String... params) {
		String getParams = "";								// Contains the GET parameters to send
		Vector<Spot> spots = new Vector<Spot>();			// List of spots to be returned
		HttpClient httpclient = new DefaultHttpClient();	// HTTP client used to execute the request
		HttpGet httpGet;									// HTTP Get header
		 
		try {	
			// If one parameter was passed in, it should be the spot ID
			// If four parameters were passed in, it should be lat & long data
			if (params.length != 1 || params.length != 4) {
				Log.e("GET spots", "Too few arguments.");
				return null;
			}
			else {
				getParams = buildGetParameterString(params);
			}

	        // Execute HTTP Get request & get the response
			httpGet = new HttpGet(RestContract.SPOTS_API + getParams);
	        HttpResponse response = httpclient.execute(httpGet);
	        
	        StatusLine status = response.getStatusLine();
	        
	        if(status.getStatusCode() == HttpStatus.SC_OK) {
	        	Log.i("GET Spots", Integer.toString(status.getStatusCode()));
	        	Log.i("GET Spots", status.getReasonPhrase());
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                spots = this.parseResults(out.toString());
                out.close();
	        }
	        
		}
		catch (Exception e) {
			Log.e("GET spots", e.getMessage());
		}
		 
		return spots;
	}
	
	/*
	 * Dismiss the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(Vector<Spot> spots) {
		super.onPostExecute(spots);
		progress.dismiss();
	}
	
	/*
	 * Builds the parameter string to append to the URL to perform a GET request.
	 * This string is returned.
	 */
	private String buildGetParameterString(String[] params) {
		String paramString = "";
		String[] getParams = {RestContract.SPOT_LAT1, RestContract.SPOT_LONG1, RestContract.SPOT_LAT2, RestContract.SPOT_LONG2};
		
		// To request a spot by ID, it simply append the ID to the end of the URL
		if (params.length == 1) {
			return paramString += params[1];
		}
		// Assuming that there were 4 parameters passed in (should be guaranteed by doInBackground)
		else {
			paramString += "?";
			for (int i = 0; i < getParams.length; i++) {
				paramString += getParams[i] + params[i];
			}
		}
		return paramString;
	}
	
	/*
	 * Takes a string containing JSON data, parses it out, and creates a vector of spot objects.
	 * This vector is returned.
	 */
	private Vector<Spot> parseResults(String results)
	{
		Vector<Spot> spots = new Vector<Spot>();

		JSONTokener tokener = new JSONTokener(results);
		try {
			JSONArray arr = new JSONArray(tokener);
			for(int i = 0 ; i < arr.length() ; ++i) {
				JSONObject json = arr.getJSONObject(i);
				spots.add(Spot.validateJSONData(json));
			}
		}
		catch(Exception e) {
			Log.e("GET Spots", "Error parsing JSON objects");
			return null;
		}
		
		return spots;
	}
}
