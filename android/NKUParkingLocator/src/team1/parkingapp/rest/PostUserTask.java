/*
 * PostUserTask.java
 * 4/2/14
 * Travis Carney
 * 
 * This class contains the task to create a new user. Before the request is made, a ProgressDialog is shown on the UI thread of
 * the Activity making the request. 
 */
package team1.parkingapp.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PostUserTask extends AsyncTask<String, Void, Void> {
	private final static String USERS_API 		= "users";
	private final static String EMAIL_FIELD 	= "email";
	private final static String PASSWORD_FIELD	= "password";
	private final static String NAME_FIELD 		= "name";
	private final static String LASTNAME_FIELD 	= "lastname";
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	public PostUserTask(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	/*
	 * Setup the ProgressDialog and then show it.
	 */
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog(ctx);
		progress.setTitle("Creating User");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
		super.onPreExecute();
	}
	
	/*
	 * Make the POST request and then get the response.
	 */
	@Override
	protected Void doInBackground(String... params) {
		    // Create a new HttpClient and Post Header
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(RestTaskFactory.BASE_API_URL + USERS_API);

		    try {
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		        
		        // Add the data to the request
		        nameValuePairs.add(new BasicNameValuePair(EMAIL_FIELD, params[0]));
		        nameValuePairs.add(new BasicNameValuePair(PASSWORD_FIELD, params[1]));
		        nameValuePairs.add(new BasicNameValuePair(NAME_FIELD, params[2]));
		        nameValuePairs.add(new BasicNameValuePair(LASTNAME_FIELD, params[3]));
		       
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP POST request & get the response
		        HttpResponse response = httpclient.execute(httppost);
		        
		        HttpEntity ent = response.getEntity();
		        
		        // For now just log the response
		        if (ent != null) {
		        	Log.i("POST Response", EntityUtils.toString(ent));
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("POST user", e.getMessage());
		    }
		return null;
	}
	
	/*
	 * Close the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(Void result) {
		progress.dismiss();
	}
}