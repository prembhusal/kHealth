package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.knoesis.health.constants.Constants;

/**
 * Creates a user based on provided information. Can only be accessed as an Admin.
 */
public class CreateUser extends KHealthAsthmaAdminActivity {
    String userGender = "";
    String userName = "";
    //String dateOfBirth = "";
    int userAge = 0;
    int userZipCode = 0;
    int userOtherZipCode = 0;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        final Spinner gender = (Spinner) findViewById(R.id.userGender);
        final EditText name = (EditText) findViewById(R.id.userName);
       // final DatePicker DOB = (DatePicker) findViewById(R.id.dateOfBirth);
        final EditText age = (EditText) findViewById(R.id.userAge);
        final EditText zipCode = (EditText) findViewById(R.id.zipCode);
        final EditText otherZipCode = (EditText) findViewById(R.id.otherZipCode);
        Button NEXT = (Button) findViewById(R.id.options_next);

        if (name.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_row, Constants.gender);
        gender.setAdapter(genderAdapter);

        NEXT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // This is where the code will go to read the entered information about the user
                userGender = gender.getSelectedItem().toString();
                userName = name.getText().toString();
                if (!zipCode.getText().toString().isEmpty() && zipCode.getText().toString().length() == 5) {
                    userZipCode = Integer.parseInt(zipCode.getText().toString());
                } else {
                    userZipCode = 0;
                }
                if (!otherZipCode.getText().toString().isEmpty() && otherZipCode.getText().toString().length() == 5) {
                    userOtherZipCode = Integer.parseInt(otherZipCode.getText().toString());
                    if (userZipCode == 0) {
                        userZipCode = userOtherZipCode;
                    }
                } else {
                    userOtherZipCode = 0;
                }

                if (userZipCode == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a zipcode.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!age.getText().toString().isEmpty() ) {
                    userAge = Integer.parseInt(age.getText().toString());
                } else {
                    userAge = 0;
                }
                //This if statement will make sure that the name field is filled out and
                //if it is not, then a dialog box will prompt the user to fill it out before moving forward.

                if (userName.equals("AdMiN")) {

                    Intent i = new Intent(CreateUser.this,
                            AdminSection.class);
                    startActivity(i);


                } else if (userName.isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    // set title
                    alertDialogBuilder.setTitle("Name");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Please enter a name before continuing.")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                        }

                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {

                   /* day = DOB.getDayOfMonth();
                    month = DOB.getMonth() + 1;
                    year = DOB.getYear();

                    dateOfBirth = year + "-" + month + "-" + day;*/

                    //Using shared preferences for all the data entered in create user page
                    SharedPreferences prefs = getSharedPreferences(userName + "Prefs", 0 /*0 for private mode*/);
                    SharedPreferences.Editor editor = prefs.edit();
                    //editor.putString("dateOfBirth", dateOfBirth);
                    editor.putInt("age", userAge);
                    editor.putString("gender", userGender);
                    editor.putInt("zipCode", userZipCode);
                    editor.putInt("otherZipCode", userOtherZipCode);
                    editor.apply();

                    Intent i = new Intent(CreateUser.this, ShortBronchodilators.class);
                    i.putExtra("name", userName);
                    //i.putExtra("dateOfBirth", dateOfBirth);
                    i.putExtra("age", userAge);
                    i.putExtra("gender", userGender);
                    i.putExtra("zipCode", userZipCode);
                    i.putExtra("otherZipCode", userOtherZipCode);
                    startActivity(i);
                    finishActivity();
                }
            }
        });


    }
}
