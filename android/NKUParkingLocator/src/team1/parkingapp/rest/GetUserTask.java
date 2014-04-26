/*
 * PostUserTask.java
 * 4/2/14
 * Travis Carney
 * 
 * This class contains the task to create a new user. Before the request is made, a ProgressDialog is shown on the UI thread of
 * the Activity making the request. 
 */
package team1.parkingapp.rest;

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
import org.json.JSONObject;
import org.json.JSONTokener;

import team1.parkingapp.data.User;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetUserTask extends AsyncTask<String, String, String> {
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	public GetUserTask(Context ctx)
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
		progress.setTitle("Logging in");
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
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(params[0], params[1]);
        ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
			
		HttpGet httpget = new HttpGet(RestContract.USERS_API);

		    try {
		        // Execute HTTP GET request & get the response
		        HttpResponse response = httpclient.execute(httpget);
		        
		        StatusLine status = response.getStatusLine();
		        
		        if(status.getStatusCode() == HttpStatus.SC_OK)
		        {
		        	//Log.i("GET User", Integer.toString(status.getStatusCode()));
		        	//Log.i("GET User", status.getReasonPhrase());
		        	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                Session.getInstance().setUser(parseResults(out.toString()));
		        	out.close();
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("GET User", e.getMessage());
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
	
	private User parseResults(String results)
	{
		JSONTokener tokener = new JSONTokener(results);
		try
		{
			JSONObject obj = new JSONObject(tokener);
			return User.validateJSONData(obj);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
}