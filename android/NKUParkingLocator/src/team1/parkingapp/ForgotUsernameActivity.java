package team1.parkingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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