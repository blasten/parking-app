/*
 * UserRegistrationActivity.java
 * 4/6/14
 * Mat Ferry & Travis Carney
 * 
 * This activity handles getting information from the user to create a new user in our application.
 * Once the user enters valid input, a POST request is sent to our backend and the user is created.
 */
package team1.parkingapp;

import team1.parkingapp.data.User;
import team1.parkingapp.rest.PostUserTask;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class UserRegistrationActivity extends Activity implements OnItemSelectedListener {
	private EditText email;			
	private EditText password;		
	private EditText confirmPwd;
	private EditText firstName;
	private EditText lastName;
	Spinner spinRole;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
		
        // Assign views to class variables
        email = (EditText) findViewById(R.id.txtUserRegistration_Email);
        password = (EditText) findViewById(R.id.txtUserRegistration_Password);
        confirmPwd = (EditText) findViewById(R.id.txtUserRegistration_ConfirmPassword);
        firstName = (EditText) findViewById(R.id.txtUserRegistration_FirstName);
        lastName = (EditText) findViewById(R.id.txtUserRegistration_LastName);
        spinRole = (Spinner) findViewById(R.id.spinUserRegistration_Role);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        		R.array.roles, android.R.layout.simple_spinner_item);
     	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     	spinRole.setAdapter(adapter);
     	spinRole.setOnItemSelectedListener(this);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Session.getInstance().getUser() != null)
    		getMenuInflater().inflate(R.menu.main_logged_in, menu);
    	else
    		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = new MainMenu(this).handleOnClick(item);
		invalidateOptionsMenu();
		return result;
	}
	
	/*
	 * This is the onClick() listener for the confirm button.
	 * If the user entered valid data then submit the user creation request.
	 */
	public void onConfirmClick(View v) {
		String emailAddr = email.getText().toString();
		String fName = firstName.getText().toString();
		String lName = lastName.getText().toString();
		String pwd = password.getText().toString();
		
		if (verifyInput()) {
			// Start the POST request
			PostUserTask userTask = RestTaskFactory.createNewUser(this, emailAddr, pwd, fName, lName);
			
			try {
				User temp = userTask.get();
				if (temp != null)
					this.finish();
			}
			catch (Exception e) {
				Log.e("User Registration", e.getMessage());
			}
		}
	}
	
	/*
	 * This is the onClick() listener for the clear button. It reset each of the fields to their default values.
	 */
	public void onClearClick(View v) {
		EditText[] fields = {email, password, confirmPwd, firstName, lastName};
		
		// Reset each value to the empty string ("")
		for (int i = 0; i < fields.length; i++)
			fields[i].setText(R.string.empty_string);
	}
	
	/*
	 * Resets the password and the confirmation password EditTexts.
	 * Useful when the user enters two passwords that don't match.
	 */
	private void resetPasswords() {
		password.setText(R.string.empty_string);
		confirmPwd.setText(R.string.empty_string);
	}
	
	/* 
	 * Checks if the user entered something in the email, password, and name fields.
	 * It also validates the email and password fields. If all of the fields are valid, then true is returned.
	 * Otherwise this method returns false.
	 */
	private boolean verifyInput() {
		String emailAddr = email.getText().toString();
		String fName = firstName.getText().toString();
		String lName = lastName.getText().toString();
		String pwd = password.getText().toString();
		String pwdConfirm = confirmPwd.getText().toString();
		
		if (!validateEmailAddress(emailAddr)) {					// Must enter a valid email address
			Toast.makeText(getApplicationContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (fName.isEmpty() || lName.isEmpty()) {			// Neither of the names can be blank
			Toast.makeText(getApplicationContext(), R.string.invalid_names, Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (pwd.isEmpty()  || pwdConfirm.isEmpty()) {		// Neither of the passwords can be blank
			Toast.makeText(getApplicationContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
			return false;
		}
		else if ( !(pwd.equals(pwdConfirm)) ) {					// The passwords must match
			Toast.makeText(getApplicationContext(), R.string.invalid_passwords, Toast.LENGTH_SHORT).show();
			resetPasswords();
			return false;
		}
		else if (pwd.length() < User.MIN_PASSWORD_LENGTH) {
			Toast.makeText(getApplicationContext(), R.string.invalid_password_length, Toast.LENGTH_SHORT).show();
			resetPasswords();
			return false;
		}
		
		return true;											// The user entered valid input, allow the create request to happen
	}
	
	/*
	 * Ensures that the user has entered a valid email address.
	 * Meaning that they didn't enter nothing and that it follows the correct format for an email address.
	 */
	private boolean validateEmailAddress(String emailAddr) {
		if (emailAddr.equals("")) // Can't have a blank address
			return false;
		
		return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddr).matches();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/*
	 * Displays a Toast on this activity. Useful for showing a Toast from another thread.
	 */
	public void showToast(final String msg) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(UserRegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
