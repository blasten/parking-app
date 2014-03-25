package team1.parkingapp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.impl.client.AbstractHttpClient;

import com.google.api.client.http.HttpRequest;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;

public class AsyncRequestHandler extends AsyncTask<String, String, String>{
	
	private String _ExecuteResults;
	
	public AsyncRequestHandler()
	{
		super();
		_ExecuteResults = null;
	}
	
	public boolean verifyUser(String url, String username, String password)
	{
		try
		{
			this.execute("GET", url, username, password).get();
		}
		catch(ExecutionException e)
		{
			_ExecuteResults = null;
		}
		catch (InterruptedException e)
		{
			_ExecuteResults = null;
		}
		if(_ExecuteResults == null || (_ExecuteResults.toUpperCase().contains("ERROR") && _ExecuteResults.toUpperCase().contains("INVALID ACCESS")))
			return false;
		else 
			return true;
	}
	
	@Override
    protected String doInBackground(String... uri)
	{
        HttpClient httpclient = new DefaultHttpClient();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(uri[2], uri[3]);
        ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
        HttpResponse response = null;
        try {
        	if(uri[0].equals("GET"))
        	{
        		HttpGet request = new HttpGet(uri[1]);
        		response = httpclient.execute(request);
        	}
        	else if (uri[1].equals("POST"))
        	{
        		HttpPost request = new HttpPost(uri[1]);
        		response = httpclient.execute(request); 
        	}
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                _ExecuteResults = out.toString();
            }
            else if (statusLine.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) 
            {
            	_ExecuteResults = "{\"error\":\"Invalid access\"}";
            	response.getEntity().getContent().close();
            }
            else
            {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
        	_ExecuteResults = "{\"error\":\"Invalid access\"}";
        } catch (IOException e) {
        	_ExecuteResults = "{\"error\":\"Invalid access\"}";
        }
        return _ExecuteResults;
    }
	
	private HttpGet Authenticate(HttpGet request, String ... uri)
	{
		if(uri.length >= 3)
		{
	    	//request.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(uri[1],uri[2]),"UTF-8",false));
	    	//request.addHeader("username",uri[1]);
	    	//request.addHeader("email",uri[1]);
	    	//request.addHeader("password",uri[2]);
		}
		return request;
	}
	
	private void setReadOnlyStatus(String result)
	{
		
	}
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
	
	private String getB64Auth(String user, String pass)
	{
		 String temp = user + ":" + pass;
		 temp = "Basic " + Base64.encodeToString(temp.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP);
		 return temp;
	}

}
