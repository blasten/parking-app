package team1.parkingapp;

import team1.parkingapp.rest.RestContract;
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
import android.widget.Toast;



public class ForgotUsernameActivity extends Activity
{
	private static final boolean SENDEMAIL_TEST = true;
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_username);
        
        //Create an onClick for the Login Button
  		this.getSendButton().setOnClickListener(new View.OnClickListener()
  		{
  	    	public void onClick(View v)
  	    	{
  	    		sendEmail(getText(getEmailTextbox()));
  	    	}
  	    } );
	}
	
	private void sendEmail(String address)
	{
		if(SENDEMAIL_TEST)
		{
			Toast.makeText(this, "Email sent to " + address, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,LoginActivity.class);
			this.startActivity(intent);
		}
		else
		{
			new AlertDialog.Builder(this)
				.setTitle("Error")
				.setMessage("Email not sent - Please Try Again")
				.setNeutralButton("OK", null)
				.show();
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return new MainMenu(this).handleOnClick(item);
	}
	
	  public boolean onCreateOptionsMenu(Menu menu) {
		if(Session.getInstance().getReservation() != null && Session.getInstance().getReservation().getStatus().equals(RestContract.RESERVED))
			getMenuInflater().inflate(R.menu.main_has_reservation, menu);
		else if(Session.getInstance().getReservation() != null && Session.getInstance().getReservation().getStatus().equals(RestContract.OCCUPIED))
			getMenuInflater().inflate(R.menu.main_is_checked_in, menu);
		else if(Session.getInstance().getUser() != null)
			getMenuInflater().inflate(R.menu.main_logged_in, menu);
		else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	  }
    
    private Button getSendButton()
    {
    	return (Button)findViewById(R.id.btnForgotUsername_Send);
    }
    private EditText getEmailTextbox()
    {
    	return (EditText)findViewById(R.id.txtForgotUsername_Email);
    }
    private String getText(EditText e)
    {
    	return e.getText().toString();
    }
}