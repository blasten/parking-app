package team1.parkingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //Add a couple users for testing purposes
        Login.addUser("user", "pass");
        Login.addUser("test", "test");
	
        //Create an onClick for the Login Button
		this.getLoginButton().setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		verifyLogin(v);
	    	}
	    } );
		
		this.getForgotLabel().setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		forgotUsernameOrPassword();
	    	}
	    } );
		
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
		String username = getText(getUsernameTextBox());
		String password = getText(getPasswordTextBox());
		
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
		if(!Login.verifyPassword(username, password))
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
		Intent intent = new Intent(this,MainActivity.class);
		this.startActivity(intent);
		return true;
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private Button getLoginButton()
    {
    	return (Button)findViewById(R.id.btnLogin_Login);
    }
    private EditText getUsernameTextBox()
    {
    	return (EditText)findViewById(R.id.txtLogin_Username);
    }
    private EditText getPasswordTextBox()
    {
    	return (EditText)findViewById(R.id.txtLogin_Password);
    }
    private TextView getForgotLabel()
    {
    	return (TextView)findViewById(R.id.lblLogin_ForgotUsername);
    }
    private String getText(EditText e)
    {
    	return e.getText().toString();
    }
}