/*
 * PostUserTask.java
 * 4/2/14
 * Travis Carney
 * 
 * This class contains the task to create a new user. Before the request is made, a ProgressDialog is shown on the UI thread of
 * the Activity making the request. 
 */
package team1.parkingapp.rest;

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

import team1.parkingapp.data.ParkingLot;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetLotTask extends AsyncTask<String, String, String> {
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	
	public GetLotTask(Context ctx)
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
			
			//Use the same method to get a single lot.  Just add on the id to the end of the uri.
			String uri = RestContract.LOTS_API;
			
		    HttpGet httpget = new HttpGet(uri);

		    try {
		        // Execute HTTP GET request & get the response
		        HttpResponse response = httpclient.execute(httpget);
		        
		        StatusLine status = response.getStatusLine();
		        
		        if(status.getStatusCode() == HttpStatus.SC_OK)
		        {
		        	//Log.i("GET Lot", Integer.toString(status.getStatusCode()));
		        	//Log.i("GET Lot", status.getReasonPhrase());
		        	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                Session.getInstance().setParkingLots(parseResults(out.toString(), params[0]));
		        	out.close();
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("GET Lot", e.getMessage());
		    }
		    
		    
		    return null;
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
	
	private Vector<ParkingLot> parseResults(String results, String role)
	{
		Vector<ParkingLot> v = new Vector<ParkingLot>();

		JSONTokener tokener = new JSONTokener(results);
		try
		{
			JSONArray arr = new JSONArray(tokener);
			for(int i = 0 ; i < arr.length() ; ++i)
			{
				JSONObject obj = arr.getJSONObject(i);
				//Have to do some data validation before trying to create the object because of nullable entries in the database
				ParkingLot temp = validateData(obj);
				if( (temp.getRole() == "student" && role.toLowerCase().contains("student")) ||
					(temp.getRole() == "faculty" && role.toLowerCase().contains("staff")) ||
					temp.getRole() == "visitor" || temp.getRole() == "All")
				{
					v.add(temp);
				}
					
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return v;
	}
	
	private ParkingLot validateData(JSONObject obj)
	{
		int id, num_spots_available;
		String name, role;
		boolean enabled;
		double lat, lng;
		
		//Try each one individually and then put in base values if there is an error.
		try
		{
			id = obj.getInt(RestContract.LOT_ID);
		}
		catch(Exception e)
		{
			id = -1;
		}
		try
		{
			num_spots_available = obj.getInt(RestContract.LOT_SPOTS_AVAIL);
		}
		catch(Exception e)
		{
			num_spots_available = -1;
		}
		try
		{
			name = obj.getString(RestContract.LOT_NAME);
		}
		catch(Exception e)
		{
			name = "";
		}
		try
		{
			enabled = obj.getBoolean(RestContract.LOT_ENABLED);
		}
		catch(Exception e)
		{
			enabled = false;
		}
		try
		{
			lat = obj.getDouble(RestContract.LOT_LAT);
		}
		catch(Exception e)
		{
			lat = 0;
		}
		try
		{
			lng = obj.getDouble(RestContract.LOT_LNG);
		}
		catch(Exception e)
		{
			lng = 0;
		}
		try
		{			
			switch(obj.getInt(RestContract.LOT_ROLE))
			{
			case 1:
				role = "faculty";
				break;
			case 2:
				role = "student";
				break;
			case 3:
				role = "visitor";
				break;
			case 4:
				role = "All";
				break;
			default:
				role = "";
				break;
			}
		}
		catch(Exception e)
		{
			role = "";
		}
		
		return new ParkingLot(id, name, enabled, num_spots_available, lat, lng, role);
		
	}
	
}