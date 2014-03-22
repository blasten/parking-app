package team1.parkingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UserRegistrationActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        this.getConfirmButton().setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		
	    	}
	    } );
		
		this.getClearButton().setOnClickListener(new View.OnClickListener()
		{
	    	public void onClick(View v)
	    	{
	    		resetInfo();
	    	}
	    } );
		
	}
	
	private void resetInfo()
	{
		clearEditText(getEditText(R.id.txtUserRegistration_Email));
		clearEditText(getEditText(R.id.txtUserRegistration_Password));
		clearEditText(getEditText(R.id.txtUserRegistration_ConfirmPassword));
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	private String getUsername(String email)
	{
		return email.substring(0, email.charAt('@'));
	}
	
	private Button getConfirmButton()
    {
    	return (Button)findViewById(R.id.btnUserRegistration_Confirm);
    }
	private Button getClearButton()
    {
    	return (Button)findViewById(R.id.btnUserRegistration_Clear);
    }
    private String getText(EditText e)
    {
    	return e.getText().toString();
    }
	private EditText getEditText(int id)
	{
		return (EditText)findViewById(id);
	}
	private void setText(EditText e, String val)
	{
		e.setText(val);
	}
	private void clearEditText(EditText e)
	{
		setText(e,"");
	}
}