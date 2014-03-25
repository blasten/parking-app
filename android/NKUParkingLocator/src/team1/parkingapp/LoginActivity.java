package team1.parkingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/*
	Need to add the Intent for the Map once it has been created

 */
public class LoginActivity extends Activity
{
	
	EditText txtEmail;
	EditText txtPassword;
	Button btnLogin;
	TextView lblForgotUsername;
	TextView lblRegister;
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
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
		
        lblForgotUsername.setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		forgotUsernameOrPassword();
	    	}
	    } );
        
        lblRegister.setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		register();
	    	}
	    } );
		
	}
	
	private void register()
	{
		Intent intent = new Intent(this,UserRegistrationActivity.class);
		this.startActivity(intent);
	}
	
	private void forgotUsernameOrPassword()
	{
		Intent intent = new Intent(this,ForgotUsernameActivity.class);
		this.startActivity(intent);
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
		if(!AsyncRequestGenerator.verifyLogin(username, password))
		{
			new AlertDialog.Builder(this)
				.setTitle("Login")
				.setMessage("Please enter a valid Username and Password")
				.setNeutralButton("OK", null)
				.show();
			return false;
		}
		
		//If there were no errors (username filled in, password filled in, password is correct for username)
		//Then goto the Project1_activity Activity
		//Toast.makeText(this, new DatabaseInteraction().testRequest(), Toast.LENGTH_LONG).show();
		
		this.finish();
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return new MainMenu(this).handleOnClick(item);
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private String getText(EditText e)
    {
    	return e.getText().toString();
    }
}