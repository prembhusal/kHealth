package org.knoesis.health;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.github.amlcurran.showcaseview.ShowcaseView;

import org.knoesis.health.adapters.UserListAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.List;

public class LoginScreenActivity extends KHealthAsthmaParentActivity {
    private List<UserDataHolder> data;

    private ShowcaseView showcaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
//
//        showcaseView = new ShowcaseView.Builder(this)
//                .setTarget(new ViewTarget(((View) findViewById(R.id.loginAsAdmin))))
//                .setContentTitle("Admin Section (Only for Clinicians)")
//                .setContentText("Create, Delete, Change and Edit Email Preferences")
//                .hideOnTouchOutside()
//                .setStyle(R.style.CustomShowcaseTheme3)
//                .build();


        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(this, LoginScreenActivity.class));
            moveTaskToBack(false);
            //finish();
            return;
        }

        if (asthmaApp.userLoggedIn != "") {
            Intent i = new Intent(LoginScreenActivity.this, HomeActivity.class);
            startActivity(i);
        }
        // Adding default names to test the adapter and listview for names
        data = db.getAllUsers();

        //Button createNewUser = (Button) findViewById(R.id.createNewUser);
        Button loginAsAdmin = (Button) findViewById(R.id.loginAsAdmin);
        ListView currentUsers = (ListView) findViewById(R.id.currentUsers);
        try {
            UserListAdapter listAdapter = new UserListAdapter(data, this,
                    ((AsthmaApplication) getApplication()));
            if (data != null) {
                currentUsers.setAdapter(listAdapter);
            }
        } catch (Exception e) {
            Intent i = new Intent(LoginScreenActivity.this, CreateUser.class);
            startActivity(i);
        }


        currentUsers.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                //Using shared Preferences for Alarm Receiver
                SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_WORLD_WRITEABLE).edit();
                String name = data.get(position).getName();
                editor.putString("Last User logged In", name);
                editor.apply();
                asthmaApp.userLoggedIn = name;

                ((AsthmaApplication) getApplication())
                        .setCurrentLoggedInUser(data.get(position).getName());
                Intent i = new Intent(LoginScreenActivity.this,
                        HomeActivity.class);
                startActivity(i);

            }

        });

		/*createNewUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginScreenActivity.this,
						CreateUser.class);
				startActivity(i);
			}
		});*/

        loginAsAdmin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Intent i = new Intent(LoginScreenActivity.this,AdminActivity.class);
                Intent i = new Intent(LoginScreenActivity.this, AdminLogin.class);
                startActivity(i);
            }
        });

    }


}

