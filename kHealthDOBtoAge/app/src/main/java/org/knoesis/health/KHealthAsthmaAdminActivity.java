package org.knoesis.health;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.knoesis.health.adapters.MenuListAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.DataHolder;
import org.knoesis.health.dataHolders.UserDataHolder;
import org.knoesis.health.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.Date;

/**
 * Parent class to all Admin based activities.
 * Created by Utkarshani on 6/2/2016.
 */
public class KHealthAsthmaAdminActivity extends Activity {
    protected PopupWindow menu;
    protected View layout;
    protected MenuListAdapter mlAdapter;

    protected int height;

    // Database helper for database
    DatabaseHandler db = new DatabaseHandler(this, Constants.DATABASE_NAME,
            Constants.DATABASE_VERSION);

    // This is used to store the current date
    Date date = new Date();

    /**
     * We put all our global variables in a class that extends Application so it
     * can be accessed in multiple activities.
     */
    AsthmaApplication asthmaApp;

    /**
     * Preferences
     */
    protected SharedPreferences sdcPreferences;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.menu_list, null);

        // Initializing the current activity

        // Get out Application so we have access to our Drone
        asthmaApp = (AsthmaApplication) getApplication();

        // Initialize SharedPreferences
        sdcPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        // this is used for to hold the options in Drop Down Menu.
        ArrayList<DataHolder> data = new ArrayList<>();

        data.add(0, new DataHolder(R.drawable.home, R.string.home));

        // Settings Activity for drop down menu
        data.add(1, new DataHolder(R.drawable.settings, R.string.Settings));

        // Change user activity for drop down menu
        data.add(2, new DataHolder(R.drawable.blue_change_user, R.string.changeUser));

        // Change user activity for drop down menu
        data.add(3, new DataHolder(R.drawable.usernew, R.string.createNewUser));

        // Delete user activity for drop down menu
        data.add(4, new DataHolder(R.drawable.deleteuser, R.string.deleteUser));

        // Settings Activity for drop down menu
        data.add(5, new DataHolder(R.drawable.final_summary,R.string.summary));

        // Button to exit the application
        data.add(6, new DataHolder(R.drawable.exitapp,  R.string.logOut));

        mlAdapter = new MenuListAdapter(data, this);
        ListView lv = ((ListView) layout.findViewById(R.id.menuListView));
        lv.setAdapter(mlAdapter);

        // Define action for each menu item clicked
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long arg3) {
                final int HOME = 0;
                final int SETTINGS = 1;
                final int CHANGE_USER = 2;
                final int CREATE_USER = 3;
                final int DELETE_USER = 4;
                final int SUMMARY = 5;
                final int LOG_OUT = 6;

                // Sets the action for the home menu button
                if (position == HOME) {
                    menu.dismiss();
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            AdminActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }// end of if

                // Sets the action for the settings menu button
                if (position == SETTINGS) {
                    menu.dismiss();

                    ((AsthmaApplication) getApplication())
                            .setSequentialCheck(true);
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            AdminActivity.class);

                    startActivity(i);
                }// end of if

                // Sets the action for the change user menu button
                if (position == CHANGE_USER) {
                    menu.dismiss();
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            ChangeUserActivity.class);

                    startActivity(i);

                }// end of if

                // Sets the action for the create user menu button
                if (position == CREATE_USER) {
                    menu.dismiss();
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            CreateUser.class);
                    startActivity(i);

                }// end of if

                // Sets the action for the delete user menu button
                if (position == DELETE_USER) {
                    menu.dismiss();
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            DeleteUser.class);
                    startActivity(i);
                }

                // sets the action for the summary menu button
               if (position == SUMMARY) {
                    menu.dismiss();
                    Intent i = new Intent(KHealthAsthmaAdminActivity.this,
                            UserSelection.class);

                    startActivity(i);

                }// end of if

                // sets the action for the log out menu button
                if (position == LOG_OUT) {
                    menu.dismiss();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            KHealthAsthmaAdminActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Are you sure?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            moveTaskToBack(false);
                                            Intent i = new Intent(KHealthAsthmaAdminActivity.this, LoginScreenActivity.class);
                                            i.putExtra("finish", true);
                                            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                            i.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    })

                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // if this button is clicked, just
                                            // close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }

            }
        });

        // Get the height of the
        lv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        height = layout.getMeasuredHeight();
        height = lv.getMeasuredHeight();
        height *= (data.size() - .5);
        menu = new PopupWindow(layout, 400, height, true);
        menu.setBackgroundDrawable(new BitmapDrawable());
        menu.setOutsideTouchable(true);

        // Remove the Title bar at the top
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // smooths gradients
        Window window = this.getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

    }

    /****
     *
     * This method will invoke when ever user clicks on drop down menu button in
     * top right corner.
     *
     * @param v The button that was clicked.
     */
    public void onClickMenu(View v) {
        // assigning the height. If the required height is more than the current
        // window size then set the current window height.
        if (height > menu.getMaxAvailableHeight(v))
            menu.setHeight(menu.getMaxAvailableHeight(v));
        else
            // otherwise set actual weight.
            menu.setHeight(height);
        menu.setWidth(500);
        menu.showAsDropDown(v, 0, -18);
    }

    @Override
    public void onStop() {
        menu.dismiss();
        super.onStop();
    }

    /*************
     *
     * When user clicks on the home button this method will invoke startup
     * activity to display home screen of the application
     *
     **************/
    public void onClickHome(View v) {

        finish();
    }

    /**
     * A function to display Toast Messages.
     *
     * By having it run on the UI thread, we will be sure that the message is
     * displays no matter what thread tries to use it.
     */
    public void quickMessage(final String msg) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    /**
     * This will add the row to the users table.
     *
     * @param name The user's name.
     * @param age The user's age.
     * @param gender The user's gender.
     * @param zipCode The user's zip code.
     * @param otherZipCode The user's secondary zip code.
     * @param albuterol 1 if albuterol was taken and 0 if not.
     * @param ventolin 1 if ventolin was taken and 0 if not.
     * @param proair 1 if proair was taken and 0 if not.
     * @param xoponex 1 if xoponex was taken and 0 if not.
     * @param atrovent 1 if atrovent was taken and 0 if not.
     * @param combivent 1 if combivent was taken and 0 if not.
     * @param duoneb 1 if duoneb was taken and 0 if not.
     * @param dulera 1 if dulera was taken and 0 if not.
     * @param symbicort 1 if symbicort was taken and 0 if not.
     * @param advair 1 if advair was taken and 0 if not.
     * @param flovent 1 if flovent was taken and 0 if not.
     * @param asmanex 1 if asmanex was taken and 0 if not.
     * @param qvar 1 if qvar was taken and 0 if not.
     * @param pulmicort 1 if pulmicort was taken and 0 if not.
     * @param budesonide 1 if budesonide was taken and 0 if not.
     * @param prednisone 1 if prednisone was taken and 0 if not.
     * @param prednisolone 1 if prednisolone was taken and 0 if not.
     * @param orapred 1 if orapred was taken and 0 if not.
     * @param montekulast 1 if montekulast was taken and 0 if not.
     * @param singulair 1 if singulair was taken and 0 if not.
     */
    public void addRowtoUsersTable(final String name, final int age,
                                   final String gender, final int zipCode ,final int otherZipCode,final int albuterol, final int ventolin,final int proair,final int xoponex,final int atrovent,
                                   final int combivent, final int duoneb, final int dulera,final int symbicort,final int advair,final int flovent,final int asmanex,
                                   final int qvar,final int pulmicort,final int budesonide,final int prednisone,final int prednisolone,
                                   final int orapred,final int montekulast,final int singulair  ) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                /*
                 * If the users table is not already created then create that
                 * users table.
                 */

                if (!db.isTableExists(Constants.USERS_TABLE_NAME)) {
                    db.createTable(Constants.CREATE_USERS_TABLE);
                    addRowtoUsersTable(name, age, gender, zipCode,otherZipCode,albuterol, ventolin, proair, xoponex, atrovent,
                     combivent, duoneb,  dulera, symbicort, advair,flovent, asmanex,
                     qvar, pulmicort, budesonide, prednisone,prednisolone,
                     orapred, montekulast,singulair  );
                } else {
                    /*
                     * If users table already exist then add the user to users
                     * table.
                     */
                    db.addRowtoUsersTable(new UserDataHolder(name, age, gender,zipCode,otherZipCode,albuterol, ventolin, proair, xoponex, atrovent,
                            combivent, duoneb,  dulera, symbicort, advair,flovent, asmanex,
                            qvar, pulmicort, budesonide, prednisone,prednisolone,
                            orapred, montekulast,singulair  ));
                    db.writeToFile();
                }
            }
        });

    }

    /**
     * Closes the current activity.
     */
    public void finishActivity() {
        this.finish();
    }
}
