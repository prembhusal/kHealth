package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminSection extends KHealthAsthmaAdminActivity {
    int tempDeploymentDates = 0;
    int tempEmailPref = 0;
    DatePicker startDate;
    DatePicker endDate;
    long convert;

    //To put current date as the subject
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");
    String strDate = mdformat.format(calendar.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_section);
        startDate = (DatePicker) findViewById(R.id.date_start);
        endDate = (DatePicker) findViewById(R.id.date_end);

        final EditText toAddress = (EditText) findViewById(R.id.editTextTo);
        final EditText cc = (EditText) findViewById(R.id.editTextCC);
        final EditText sub = (EditText) findViewById(R.id.editTextSubject);
        final EditText numberofdaysunanswered = (EditText) findViewById(R.id.editTextNOD);
        final EditText messageBody = (EditText) findViewById(R.id.editTextMessage);
        final TextView diff = (TextView) findViewById(R.id.tvDifference);
        Button emailPref = (Button) findViewById(R.id.emailButton);
        Button deployPref = (Button) findViewById(R.id.DeploymentButton);
        Button deployButton = (Button) findViewById(R.id.saveDepDates);
        Button buttonSaveEmail = (Button) findViewById(R.id.buttonSave);
        final LinearLayout email = (LinearLayout) findViewById(R.id.EmailLayout);
        final LinearLayout dates = (LinearLayout) findViewById(R.id.LinearLayoutDates);

        String user = null;


        final List<UserDataHolder> users = db.getAllUsers();

        if (user == null) {
            showAlert(users, numberofdaysunanswered, cc, toAddress, sub, messageBody, user, diff);

        }

        email.setVisibility(View.GONE);

        dates.setVisibility(View.GONE);
        emailPref.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tempEmailPref == 0) {
                    email.setVisibility(View.VISIBLE);

                    tempEmailPref = 1;
                } else {

                    email.setVisibility(View.GONE);
                    tempEmailPref = 0;
                }

            }
        });

        deployPref.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tempDeploymentDates == 0) {
                    dates.setVisibility(View.VISIBLE);

                    tempDeploymentDates = 1;
                } else {

                    dates.setVisibility(View.GONE);
                    tempDeploymentDates = 0;

                }

            }
        });

        deployButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                setDeployDate(convert);
                Toast.makeText(getApplicationContext(),
                        "Saved the number of days deployed under preferences",
                        Toast.LENGTH_LONG).show();

                email.setVisibility(View.GONE);

                dates.setVisibility(View.GONE);
                tempEmailPref = 0;
                tempDeploymentDates = 0;

            }
        });

        final Calendar startCal = Calendar.getInstance();
        final Calendar endCal = Calendar.getInstance();


        Log.d("Calendar Year", startCal.get(Calendar.YEAR) + "");
        Log.d("Calendar Month", startCal.get(Calendar.MONTH) + "");


        startDate.init(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // minCal.set(Calendar.YEAR, year - 1);
                // endDatePicker.setMinDate(minCal.getTimeInMillis());
                // minCal.set(Calendar.YEAR, year);
                // minCal.set(Calendar.MONTH, monthOfYear);
                // minCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // minCal.set(Calendar.HOUR_OF_DAY, 0);
                // minCal.set(Calendar.MINUTE, 0);
                // endDatePicker.setMinDate(minCal.getTimeInMillis());
                startCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startCal.set(Calendar.MONTH, monthOfYear);
                startCal.set(Calendar.YEAR, year);

                setDifference(startCal, endCal, diff);

            }

        });

        endDate.init(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH) + 20, new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // minCal.set(Calendar.YEAR, year - 1);
                // endDatePicker.setMinDate(minCal.getTimeInMillis());
                // minCal.set(Calendar.YEAR, year);
                // minCal.set(Calendar.MONTH, monthOfYear);
                // minCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // minCal.set(Calendar.HOUR_OF_DAY, 0);
                // minCal.set(Calendar.MINUTE, 0);
                // endDatePicker.setMinDate(minCal.getTimeInMillis());

                endCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endCal.set(Calendar.MONTH, monthOfYear);
                endCal.set(Calendar.YEAR, year);

                setDifference(startCal, endCal, diff);

            }

        });

        buttonSaveEmail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                setDefaultAdminPrefValues(numberofdaysunanswered, cc, toAddress, sub, messageBody);
                Toast.makeText(getApplicationContext(),
                        "Saved the email preferences", Toast.LENGTH_LONG)
                        .show();

                email.setVisibility(View.GONE);

                dates.setVisibility(View.GONE);
                tempEmailPref = 0;
                tempDeploymentDates = 0;

            }
        });

    }

    private void showAlert(final List<UserDataHolder> users, final EditText numberofdaysunanswered, final EditText cc, final EditText toAddress, final EditText sub, final EditText messageBody, final String user, final TextView diff) {


        List<String> list = new ArrayList<String>();
        for (int i = 0; i < users.toArray().length; i++) {
            list.add(users.get(i).getName());
        }
        final CharSequence[] items = list.toArray(new CharSequence[list.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminSection.this);

        builder.setTitle("Select Current User for Setting the Preferences");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                SharedPreferences.Editor editor = getSharedPreferences(
                        Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("Last User logged In", items[item].toString());
                editor.commit();
                ((AsthmaApplication) getApplication())
                        .setCurrentLoggedInUser(items[item].toString());
                setEmailPrefTextFields(numberofdaysunanswered, cc, toAddress, sub, messageBody, user, diff);


            }
        });
        builder.show();
    }

    public void setDifference(Calendar start, Calendar end, TextView diff) {

        long difference = start.getTimeInMillis() - end.getTimeInMillis();

        convert = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

        diff.setText("" + Math.abs(convert));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_section, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setDefaultAdminPrefValues(EditText numberofdaysunanswered, EditText cc, EditText toAddress, EditText sub, EditText messageBody) {
        SharedPreferences.Editor editor = getSharedPreferences(
                Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("numberofdaysunanswered",
                numberofdaysunanswered.getText().toString());

        editor.putString("emailTo", toAddress.getText().toString());
        editor.putString("ccTo", cc.getText().toString());
        editor.putString("subject", sub.getText().toString());
        editor.putString("Message", messageBody.getText().toString());
        editor.putString("state", "custom");
        editor.commit();
    }

    public void setDeployDate(long convert) {
        SharedPreferences.Editor editor = getSharedPreferences(
                Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("numberofdeploymentdays", (int) Math.abs(convert));
        editor.commit();
    }

    public void setEmailPrefTextFields(EditText numberofdaysunanswered, EditText cc, EditText toAddress, EditText sub, EditText messageBody, String user, TextView diff) {
        SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME,
                MODE_WORLD_READABLE);
        diff.setText(prefs.getInt("numberofdeploymentdays", 0) + "");
        toAddress.setText(prefs.getString("emailTo", "KalraM@childrensdayton.org"));
        cc.setText(prefs.getString("ccTo", "khealthuser1@gmail.com"));
        sub.setText(prefs.getString("Last User logged In", null), TextView.BufferType.EDITABLE);
        numberofdaysunanswered.setText(prefs.getString("numberofdaysunanswered", "1"));

        messageBody.setText("User" + "\t" + prefs.getString("Last User logged In", null) + "\t" + "did not answer the questions today.", TextView.BufferType.EDITABLE);

    }


}
