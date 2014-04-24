/*
 * AccountSettingsActivity.java
 * 4/23/14
 * Travis Carney
 * 
 * This activity allows the user to change their password.
 */
package team1.parkingapp;

import team1.parkingapp.data.User;
import team1.parkingapp.rest.PutUserTask;
import team1.parkingapp.rest.RestTaskFactory;
import team1.parkingapp.rest.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AccountSettingsActivity extends Activity {
	private User user;				// Tracks the logged in user
	EditText passwordCurrent;		// Contains the user's current password
	EditText passwordNew;			// Contains a new password
	EditText passwordConfirmed;		// Contains a confirmed new password
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_settings_layout);
		
		// Setup references to class objects
		passwordCurrent = (EditText) findViewById(R.id.txtOldPassword);
		passwordNew = (EditText) findViewById(R.id.txtNewPassword);
		passwordConfirmed = (EditText) findViewById(R.id.txtConfirmedPassword);
		user = Session.getInstance().getUser();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invalidateOptionsMenu();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/*
	 * When a user clicks the confirm button, this event is fired.
	 */
	public void onConfirmClicked(View v) {
		String email = this.user.getEmail();
		String oldPwd = this.user.getPassword();
		
		if (validatePasswords()) {
			PutUserTask updateUser = RestTaskFactory.putUser(this, email, oldPwd, null, getPasswordNew(), null, null);
			
			try {
				User temp = updateUser.get();
				if (temp != null) {
					String newPwd = temp.getPassword();
					Session.getInstance().getUser().setPassword(newPwd);
					Toast.makeText(getApplicationContext(), R.string.password_changed, Toast.LENGTH_SHORT).show();
					this.finish();
				}
			}
			catch (Exception e) {
				Log.e("Account Settings", "Error changing user password");
			}
		}
	}
	
	/*
	 * When the user clicks the clear button, clear out the password fields.
	 */
	public void onClearClicked(View v) {
		resetPasswords();
	}
	
	/*
	 * Checks that the passwords entered are valid.
	 */
	private boolean validatePasswords() {
		String current = getPasswordCurrent();
		String newPwd = getPasswordNew();
		String confirmed = getPasswordConfirmed();
		
		// The old password must match
		if ( !current.equals(user.getPassword()) ) {
			Toast.makeText(getApplicationContext(), R.string.old_password_bad, Toast.LENGTH_SHORT);
			return false;
		}
		
		// Neither can be empty
		if (newPwd.isEmpty() || confirmed.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		// The passwords must match
		else if ( !newPwd.equals(confirmed) ) {
			Toast.makeText(getApplicationContext(), R.string.invalid_passwords, Toast.LENGTH_SHORT).show();
			resetPasswords();
			return false;
		}
		
		// The passwords must be at least 4 characters long
		else if (newPwd.length() < User.MIN_PASSWORD_LENGTH) {
			Toast.makeText(getApplicationContext(), R.string.invalid_password_length, Toast.LENGTH_SHORT).show();
			resetPasswords();
			return false;
		}
		
		return true;	// All fields checkout and are valid
	}
	
	/*
	 * Reset the passwords to the empty string.
	 */
	private void resetPasswords() {
		this.passwordCurrent.setText("");
		this.passwordNew.setText("");
		this.passwordConfirmed.setText("");
	}
	
	/*
	 * Accessors and mutators follow.
	 */
	
	private String getPasswordCurrent() {
		return this.passwordCurrent.getText().toString();
	}
	
	private String getPasswordNew() {
		return this.passwordNew.getText().toString();
	}
	
	private String getPasswordConfirmed() {
		return this.passwordConfirmed.getText().toString();
	}

}
