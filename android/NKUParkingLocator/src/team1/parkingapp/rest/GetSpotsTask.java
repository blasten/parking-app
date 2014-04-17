/*
 * GetSpotsTask.java
 * 4/16/14
 * Travis Carney
 * 
 * This class contains the implementation for the GET spots request.
 */
package team1.parkingapp.rest;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import team1.parkingapp.data.Spot;

public class GetSpotsTask extends AsyncTask<String, Void, ArrayList<Object> > {

	@Override
	protected void onPreExecute() {
		
	}
	
	@Override
	protected ArrayList<Object> doInBackground(String... params) {
		String getParams = "";								// Contains the GET parameters to send
		ArrayList<Spot> spots = new ArrayList<Spot>();		// List of spots to be returned
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

			httpGet = new HttpGet(RestContract.SPOTS_API + getParams);
	        // Execute HTTP Get request & get the response
	        HttpResponse response = httpclient.execute(httpGet);
	        
	        HttpEntity ent = response.getEntity();
	        
	        // For now just log the response
	        if (ent != null) {
	        	Log.i("GET Response", EntityUtils.toString(ent));
	        }
		}
		catch (IOException ioe) {
			Log.e("GET spots", ioe.getMessage());
		}
		 
		return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Object> spots) {
		
	}
	
	private String buildGetParameterString(String[] params) {
		String paramString = "";
		String[] getParams = {RestContract.LAT1, RestContract.LONG1, RestContract.LAT2, RestContract.LONG2};
		
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
}
