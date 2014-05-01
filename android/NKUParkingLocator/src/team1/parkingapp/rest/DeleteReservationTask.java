package team1.parkingapp.rest;

import java.util.Locale;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DeleteReservationTask  extends AsyncTask<String, String, String> {
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	
	public DeleteReservationTask(Context ctx)
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
		    	
		    	UsernamePasswordCredentials creds = new UsernamePasswordCredentials(params[1], params[2]);
		        ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
		    	
		    	HttpDelete httpDelete = new HttpDelete(RestContract.RESERVATIONS_API + params[0]);
		    	
		        // Execute HTTP GET request & get the response
		        HttpResponse response = httpclient.execute(httpDelete);
		        
		        StatusLine status = response.getStatusLine();
		        
		        if(status.getStatusCode() != HttpStatus.SC_OK)
		        {
		        	throw new Exception();
		        }
		        else
		        {
		        	Session.getInstance().setReservation(null);
		        	ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                String s = out.toString();
	                out.close();
	                if(s.toLowerCase(Locale.ENGLISH).contains("error"))
	                {
	                	return null;
	                }
	                return s;
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("GET Lot", e.getMessage());
		    	return null;
		    }
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