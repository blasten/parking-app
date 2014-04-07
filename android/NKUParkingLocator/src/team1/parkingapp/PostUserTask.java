/*
 * PostUserTask.java
 * 4/2/14
 * Travis Carney
 * 
 * This class contains task to create a new user.
 */
package team1.parkingapp;

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
import android.os.AsyncTask;
import android.util.Log;

public class PostUserTask extends AsyncTask<String, Void, Void> {
	private String response = "";

	@Override
	protected Void doInBackground(String... params) {
		    // Create a new HttpClient and Post Header
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://parking-app.herokuapp.com/api/users");

		    try {
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		        
		        // Add the data to the request
		        nameValuePairs.add(new BasicNameValuePair("email", params[0]));
		        nameValuePairs.add(new BasicNameValuePair("password", params[1]));
		        nameValuePairs.add(new BasicNameValuePair("name", params[2]));
		        nameValuePairs.add(new BasicNameValuePair("lastname", params[3]));
		       
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP POST request & get the response
		        HttpResponse response = httpclient.execute(httppost);
		        
		        HttpEntity ent = response.getEntity();
		        
		        // For now just log the response
		        if (ent != null) {
		        	Log.i("POST Response", EntityUtils.toString(ent));
		        	this.response = EntityUtils.toString(ent);
		        }

		    } 
		    catch (Exception e) {
		    	Log.e("POST user", e.getMessage());
		    }
		return null;
	}

	public String getResponse() {
		return this.response;
	}

}