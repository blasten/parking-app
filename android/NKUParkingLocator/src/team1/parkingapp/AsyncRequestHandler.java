package team1.parkingapp;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
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

import com.google.api.client.http.HttpRequest;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;

public class AsyncRequestHandler extends AsyncTask<String, String, String>{
	
	private String _requestType = null;
	
	public AsyncRequestHandler()
	{
		super();
	}
	
	public AsyncRequestHandler(String _requestType)
	{
		super();
		this._requestType = _requestType;
	}
	
	@Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
        	HttpGet request = new HttpGet(uri[0]);
        	request = Authenticate(request, uri);
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            }
            else if (statusLine.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) 
            {
            	responseString = "{\"error\":\"Invalid access\"}";
            	response.getEntity().getContent().close();
            }
            else
            {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
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
		setReadOnlyStatus(result);
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
