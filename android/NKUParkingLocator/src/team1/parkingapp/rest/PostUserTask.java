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

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import team1.parkingapp.UserRegistrationActivity;
import team1.parkingapp.data.User;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import team1.parkingapp.R;

public class PostUserTask extends AsyncTask<String, Void, User> {
	private ProgressDialog progress;	// ProgressDialog to let the user know the app is working
	private Context ctx;				// Context on which to show the ProgressDialog

	/*
	 * Just assign the context of the Activity to the ctx member variable.
	 */
	protected PostUserTask(Context ctx) {
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
		progress.setTitle("Creating User");
		progress.setMessage("Please wait.");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
	}
	
	/*
	 * Make the POST request and then get the response.
	 */
	@Override
	protected User doInBackground(String... params) {
		User user = null;											// User to be returned
	    HttpClient httpclient = new DefaultHttpClient();			// HTTP client used to perform the request;
	    HttpPost httppost = new HttpPost(RestContract.USERS_API);	// HTTP POST header
	    String[] postKeys = 										// Array of keys to be sent to the server
	    	{RestContract.USER_EMAIL, RestContract.USER_PASSWORD, RestContract.USER_NAME, RestContract.USER_LASTNAME};

	    // There should be exactly 4 parameters passed in
	    if (params.length != 4) {
	    	Log.e("POST user", "Incorrect number of parameters received.");
	    	return null;
	    }
	    
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	        
	        // Add the data to the request
	        for (int i = 0; i < postKeys.length; i++) 
	        	nameValuePairs.add(new BasicNameValuePair(postKeys[i], params[i]));
	       
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP POST request & get the response
	        HttpResponse response = httpclient.execute(httppost);
	        StatusLine status = response.getStatusLine();
	        
	        if(status.getStatusCode() == HttpStatus.SC_CREATED) {
	        	Log.i("POST User", Integer.toString(status.getStatusCode()));
	        	Log.i("POST User", status.getReasonPhrase());
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                user = this.parseResults(out.toString());
                out.close();
	        }
	        else
	        {
	        	throw new Exception();
	        }
	    } 
	    catch (Exception e) {
	    	Log.e("POST User", e.getMessage());
	    	user = null;
	    }
	    
	    Session.setUser(user);
	    return Session.getUser();
	}
	
	/*
	 * Close the ProgressDialog.
	 */
	@Override
	protected void onPostExecute(User user) {
		super.onPostExecute(user);
		progress.dismiss();
	}
	
	/*
	 * Takes in a string containing JSON data, validates it, and returns a user object.
	 */
	private User parseResults(String results) {
		User user = null;
		
		Log.i("Result", results);
	
		JSONTokener tokener = new JSONTokener(results);
		try {
			JSONObject json = new JSONObject(tokener);
			user = User.validateJSONData(json);
			// If the user came back null, it means the email was in use
			if (user == null)
				((UserRegistrationActivity) ctx).showToast(ctx.getResources().getString(R.string.email_taken));
			// If the user isn't null, it was created successfully. Show a Toast.
			else
				((UserRegistrationActivity) ctx).showToast(ctx.getResources().getString(R.string.user_created));
		}
		catch(Exception e) {
			Log.e("POST User", "Error parsing JSON objects");
			return null;
		}
		
		return user;
	}
}