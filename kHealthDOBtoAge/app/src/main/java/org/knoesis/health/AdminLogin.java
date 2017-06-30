package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.knoesis.health.constants.Constants;


/**
 * A login screen that offers login via email/password.
 */
public class AdminLogin extends KHealthAsthmaAdminActivity {

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;

	/**
	 * Verifies the user login information
	 * @param username The username to be checked
	 * @param password The password to be checked
     * @return True if username/password is valid, false otherwise.
     */
	public boolean checkUser(String username, String password){
		boolean valid = false;
		if(db.isTableExists(Constants.ADMIN_TABLE)){
			valid = password.equals(db.getUserPassword(username));
		}
		return valid;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		// Set up the login form.
		mUsernameView = (EditText) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				// Get username and password
				String name = mUsernameView.getText().toString().trim();
				String pass = mPasswordView.getText().toString().trim();

				// Encrypt the password
				pass = new String(Hex.encodeHex(DigestUtils.sha1(pass)));
				if(checkUser(name, pass)){
					Intent i = new Intent(AdminLogin.this,
							AdminActivity.class);
					startActivity(i);
					finishActivity();
				}else{
					Toast.makeText(getBaseContext(), "Invalid username or password.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}

