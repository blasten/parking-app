/*
 * This is the code for the Login Activity.  It will handle verifying that the email
 * and password combination is in the database and will give the users read-write
 * access to our application.  Until the user is logged in, they will only have
 * read access.
 */

package team1.parkingapp;

import java.util.concurrent.TimeUnit;

import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	
	//Set references to the different views.
	EditText txtEmail;
	EditText txtPassword;
	Button btnLogin;
	TextView lblForgotUsername;
	TextView lblRegister;
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //Set the references
        txtEmail = (EditText)findViewById(R.id.txtLogin_Username);
        txtPassword = (EditText)findViewById(R.id.txtLogin_Password);
        btnLogin = (Button)findViewById(R.id.btnLogin_Login);
        lblForgotUsername = (TextView)findViewById(R.id.lblLogin_ForgotUsername);
        lblRegister = (TextView)findViewById(R.id.lblLogin_Register);
        
        //Create an onClick for the Login Button
        btnLogin.setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		verifyLogin(v);
	    	}
	    } );
        //Create an onClick for the Forgot Username label
        lblForgotUsername.setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		forgotUsernameOrPassword();
	    	}
	    } );
      //Create an onClick for the Register label
        lblRegister.setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		register();
	    	}
	    } );
		
        // Already logged in, shouldn't be here.
        if (Session.getInstance().getUser() != null) {
        	this.finish();
        }
	}
	
	//If the Register Label is clicked then start the User Registration Activity
	private void register()
	{
		Intent intent = new Intent(this,UserRegistrationActivity.class);
		this.startActivityForResult(intent, 1);
	}
	
	//If the Register Label is clicked then start the ForgotUsername Activity
	private void forgotUsernameOrPassword()
	{
		Intent intent = new Intent(this,ForgotUsernameActivity.class);
		this.startActivityForResult(intent, 1);
	}
	
	//This will verify the username and password in the username and password edittexts
	private boolean verifyLogin(View view)
	{
		//Get the text out of the 2 edit texts
		String username = getText(txtEmail);
		String password = getText(txtPassword);
		
		//Print out any errors
		if(username.equals(""))
		{
			Toast.makeText(this, "Please enter a username.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(password.equals(""))
		{
			Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
			return false;
		}
		try
		{
			RestTaskFactory.getUser(this, username, password).get(15, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			new AlertDialog.Builder(this)
				.setTitle("Login")
				.setMessage("Network Connection is unstable.  Please try again.")
				.setNeutralButton("OK", null)
				.show();
			return false;
		}
		if(Session.getInstance().getUser() == null)
		{
			new AlertDialog.Builder(this)
				.setTitle("Login")
				.setMessage("Please enter a valid Username and Password")
				.setNeutralButton("OK", null)
				.show();
			return false;
		}
		
		// Need to set the password
		Session.getInstance().getUser().setPassword(password);
		//If the user is valid then close this activity and return true (not used)
		Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this,MainActivity.class);
		clearFields();
		startActivity(i);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//Set up the menu
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean result = new MainMenu(this).handleOnClick(item);
		invalidateOptionsMenu();
		return result;
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(Session.getInstance().getUser() != null)
    		getMenuInflater().inflate(R.menu.main_logged_in, menu);
    	else
    		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Get the String Text out of an edit text.
    private String getText(EditText e)
    {
    	return e.getText().toString();
    }
    
    /*
     * Clears the email and password fields.
     */
    private void clearFields() {
    	this.txtEmail.setText("");
    	this.txtPassword.setText("");
    }
}