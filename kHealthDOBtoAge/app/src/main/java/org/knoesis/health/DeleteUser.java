package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.knoesis.health.adapters.UserListAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.List;

/**
 * Provides a list of users that can be deleted from the user's table.
 * Created by Utkarshani on 7/9/2016.
 *
 * Modified on 03/19/2017
 */
public class DeleteUser extends KHealthAsthmaAdminActivity {
    private List<UserDataHolder> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        data = db.getAllUsers();

        UserListAdapter listAdapter = new UserListAdapter(data, this,
                ((AsthmaApplication) getApplication()));
        ListView currentUsers = (ListView) findViewById(R.id.currentUsers);
        currentUsers.setAdapter(listAdapter);

        currentUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("User Deleted", data.get(position).getName());
                editor.apply();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DeleteUser.this);

                // set title
                alertDialogBuilder.setTitle("Delete User");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete" + data.get(position).getName())
                        .setCancelable(false)
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // ((AsthmaApplication) getApplication()).setCurrentLoggedInUser(data.get(position).getName());
                                        db.deleteUserRecord(data.get(position).getName());

                                        Intent i = new Intent(DeleteUser.this, AdminActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }

                                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog, int id) {
// if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }

                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });
    }
}

