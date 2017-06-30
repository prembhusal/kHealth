package org.knoesis.health;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.knoesis.health.adapters.UserListAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.List;

public class ChangeUserActivity extends KHealthAsthmaAdminActivity {
	private List<UserDataHolder> data;
    /*private void showAlert(){
        AlertDialog alert = new AlertDialog.Builder(ChangeUserActivity.this).create();
        alert.setTitle("No User");
        alert.setMessage("There are no existing Users! Please create new user");
        alert.setIcon(R.drawable.warning_alert);
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Back",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Please create a user",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ChangeUserActivity.this, AdminActivity.class);
                        startActivity(i);
                    }

                });
        alert.show();
    }*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_user);

		data = db.getAllUsers();

		UserListAdapter listAdapter = new UserListAdapter(data, this,
				((AsthmaApplication) getApplication()));
		ListView currentUsers = (ListView) findViewById(R.id.currentUsers);
		currentUsers.setAdapter(listAdapter);

		/*if(currentUsers.equals(null)){
            showAlert();
        }*/
		currentUsers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				 SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
				 editor.putString("Last User logged In",data.get(position).getName());
				 editor.commit();
				((AsthmaApplication) getApplication())
						.setCurrentLoggedInUser(data.get(position).getName());
				Intent i = new Intent(ChangeUserActivity.this,
						AdminActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
			}

		});


	}

}
