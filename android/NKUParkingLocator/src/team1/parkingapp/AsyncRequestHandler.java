/*
 * This will actually handle the Asynchronous tasks and return the results back to
 * the Generator.  Right now there will be separate functions for each type of
 * request so that we can return the values that we need to, but that may change later.
 * 
 * 
 */

package team1.parkingapp;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.AbstractHttpClient;
import android.os.AsyncTask;

public class AsyncRequestHandler extends AsyncTask<String, String, String>{
	
	//This will hold the results of the execution
	private String _ExecuteResults;
	private boolean _ReceivedError;
	
	//Constructor
	public AsyncRequestHandler()
	{
		super();
		_ExecuteResults = null;
		_ReceivedError = false;
	}
	
	//This will execute a request for a username and password
	public boolean verifyUser(String url, String username, String password)
	{
		//Try to execute the Http request and if you get any errors just assume they are not a valid user.
		//This way they will have to try again.
		try
		{
			this.execute("GET", url, username, password).get();
		}
		catch(Exception e)
		{
			_ReceivedError = true;
		}
		
		//If there was an error or they are not a user return false - otherwise return true
		if(_ReceivedError)
			return false;
		else 
			return true;
	}
	
	@Override
    protected String doInBackground(String... uri)
	{
		//The uri should be {request type, url, username, password, extra...}
		if(uri.length < 4)
			return "{\"error\":\"Invalid access\"}";
		
		//Create an http client and set the authentication
        HttpClient httpclient = new DefaultHttpClient();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(uri[2], uri[3]);
        ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
        
        //Set a reference to the response before setting up the request because it will be different depending on request type
        HttpResponse response = null;
        
        try {
        	
        	//uri[0] = request type so set up the requests based on that.
        	if(uri[0].equals("GET"))
        	{
        		HttpGet request = new HttpGet(uri[1]);
        		response = httpclient.execute(request);
        	}
        	else if (uri[0].equals("POST"))
        	{
        		HttpPost request = new HttpPost(uri[1]);
        		response = httpclient.execute(request); 
        	}
        	
        	//Check the status of the request
            StatusLine statusLine = response.getStatusLine();
            
            //If all is well then get the information out and stick it in _ExecuteResults
            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                _ExecuteResults = out.toString();
            }
            //Otherwise Close the connection and mark that we received an error
            else
            {
                response.getEntity().getContent().close();
                _ReceivedError = true;
            }
        }
        catch (Exception e)
        {
        	_ReceivedError = true;
        }
        return _ExecuteResults;
    }
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
