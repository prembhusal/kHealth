package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.knoesis.health.adapters.HomeIconAdapter;
import org.knoesis.health.dataHolders.HomeIconDataHolder;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin home page
 * Created by Utkarshani on 6/1/2016.
 */

public class AdminActivity extends KHealthAsthmaAdminActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ArrayList<HomeIconDataHolder> data = new ArrayList<>();

        data.add(new HomeIconDataHolder(R.drawable.settings, R.string.action_settings));

        data.add(new HomeIconDataHolder(R.drawable.blue_change_user, R.string.changeUser));

        data.add(new HomeIconDataHolder(R.drawable.final_summary, R.string.summary));

        data.add(new HomeIconDataHolder(R.drawable.usernew, R.string.createNewUser));

        data.add(new HomeIconDataHolder(R.drawable.deleteuser, R.string.deleteUser));
        // Get the list of all users
        final List<UserDataHolder> users = db.getAllUsers();


        if (!checkIfUserExists(users)) {
            showAlert();
        }


        // load icons into grid view
        GridView gridview = (GridView) findViewById(R.id.mainIconGridView);
        gridview.setAdapter(new HomeIconAdapter(data, this));


        // Set up the grid view click listeners
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                final int HOME = 0;
                final int CHANGE_USER = 1;
                final int SUMMARY = 2;
                final int CREATE_USER = 3;
                final int DELETE_USER = 4;

                // set action for the home option
                if (position == HOME) {
                    if (checkIfUserExists(users)) {
                        Intent i;
                        i = new Intent(AdminActivity.this, AdminSection.class);
                        startActivity(i);
                    } else {
                        showAlert();
                    }
                }

                // set action for the change user option
                if (position == CHANGE_USER) {
                    if (checkIfUserExists(users)) {
                        Intent i = new Intent(AdminActivity.this, ChangeUserActivity.class);
                        startActivity(i);
                    } else {
                        showAlert();
                    }
                }

                // set action for the summary option
                if (position == SUMMARY) {
                    if (checkIfUserExists(users)) {
                        Intent i = new Intent(AdminActivity.this, UserSelection.class);
                        startActivity(i);
                    } else {
                        showAlert();
                    }
                }

                // set action for the create user option
                if (position == CREATE_USER) {
                    Intent i = new Intent(AdminActivity.this, CreateUser.class);
                    startActivity(i);
                }

                // set action for the delete user option
                if (position == DELETE_USER) {
                    Log.d("users", String.valueOf(users));
                    if (checkIfUserExists(users)) {
                        Intent i = new Intent(AdminActivity.this, DeleteUser.class);
                        startActivity(i);
                    } else {
                        showAlert();
                    }
                }
            }
        });


    }

    private boolean checkIfUserExists(List<UserDataHolder> users) {
        if (users != null && users.size() > 0)
            return true;
        else
            return false;
    }

    public void showAlert() {
        new AlertDialog.Builder(AdminActivity.this)
                .setTitle("Warning!")
                .setMessage("There are currently no users. Please create a user.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
