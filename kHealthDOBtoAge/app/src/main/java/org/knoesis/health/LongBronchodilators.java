package org.knoesis.health;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Swati on 7/10/2016.
 */

public class LongBronchodilators extends KHealthAsthmaAdminActivity {


    String userN;
    String userG;
    //String userDO;
    int userA;
    int zip;
    int oth_zip;
    int ans_albuterol;
    int ans_ventolin;
    int ans_proair;
    int ans_xoponex;
    int ans_atrovent;
    int ans_combivent;
    int ans_duoneb;
    int answer_dulera;
    int answer_symbicort;
    int answer_advair;
    int answer_flovent;
    int answer_asmanex;
    int answer_qvar;
    int answer_pulmicort;
    int answer_budesonide;
    int answer_prednisone;
    int answer_prednisolone;
    int answer_orapred;
    int answer_montekulast;
    int answer_singulair;

    final Context context = this;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_long_acting_bronchodilators);
        final CheckBox dulera = (CheckBox) findViewById(R.id.check1);
        final CheckBox symbicort = (CheckBox) findViewById(R.id.check2);
        final CheckBox advair = (CheckBox) findViewById(R.id.check3);
        final CheckBox flovent = (CheckBox) findViewById(R.id.check4);
        final CheckBox asmanex = (CheckBox) findViewById(R.id.check5);
        final CheckBox qvar = (CheckBox) findViewById(R.id.check6);
        final CheckBox pulmicort = (CheckBox) findViewById(R.id.check7);
        final CheckBox budesonide = (CheckBox) findViewById(R.id.check8);
        final CheckBox prednisone = (CheckBox) findViewById(R.id.check9);
        final CheckBox prednisolone = (CheckBox) findViewById(R.id.check10);
        final CheckBox orapred = (CheckBox) findViewById(R.id.check11);
        final CheckBox montelukast = (CheckBox) findViewById(R.id.check12);
        final CheckBox singulair = (CheckBox) findViewById(R.id.check13);
        Button SAVE = (Button) findViewById(R.id.saveUser);
        Button previous = (Button) findViewById(R.id.previous);

        final Intent intent = getIntent();
        userN = intent.getStringExtra("name");
        //userDO = intent.getStringExtra("dateOfBirth");
        userA = intent.getIntExtra("age",0);
        userG = intent.getStringExtra("gender");
        zip = intent.getIntExtra("zipCode", 0);
        oth_zip = intent.getIntExtra("otherZipCode", 0);
        ans_albuterol = intent.getIntExtra("albuterol", -1);
        ans_ventolin = intent.getIntExtra("ventolin", -1);
        ans_proair = intent.getIntExtra("proair", -1);
        ans_xoponex = intent.getIntExtra("xoponex", -1);
        ans_atrovent = intent.getIntExtra("atrovent", -1);
        ans_combivent = intent.getIntExtra("combivent", -1);
        ans_duoneb = intent.getIntExtra("duoneb", -1);

        SAVE.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {

                                        if (dulera.isChecked()) {
                                            answer_dulera = 1;

                                        }
                                        if (symbicort.isChecked()) {
                                            answer_symbicort = 1;

                                        }
                                        if (advair.isChecked()) {
                                            answer_advair = 1;

                                        }
                                        if (flovent.isChecked()) {
                                            answer_flovent = 1;

                                        }
                                        if (asmanex.isChecked()) {
                                            answer_asmanex = 1;

                                        }
                                        if (qvar.isChecked()) {
                                            answer_qvar = 1;

                                        }
                                        if (pulmicort.isChecked()) {
                                            answer_pulmicort = 1;

                                        }
                                        if (budesonide.isChecked()) {
                                            answer_budesonide = 1;

                                        }
                                        if (prednisone.isChecked()) {
                                            answer_prednisone = 1;

                                        }
                                        if (prednisolone.isChecked()) {
                                            answer_prednisolone = 1;

                                        }
                                        if (orapred.isChecked()) {
                                            answer_orapred = 1;

                                        }
                                        if (montelukast.isChecked()) {
                                            answer_montekulast = 1;

                                        }
                                        if (singulair.isChecked()) {
                                            answer_singulair = 1;

                                        }

                                        SharedPreferences prefs = getSharedPreferences(userN + "Prefs", 0);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putInt("dulear", answer_dulera);
                                        editor.putInt("symbicort", answer_symbicort);
                                        editor.putInt("advair", answer_advair);
                                        editor.putInt("flovent", answer_flovent);
                                        editor.putInt("asmanex", answer_asmanex);
                                        editor.putInt("qvar", answer_qvar);
                                        editor.putInt("pulmicort", answer_pulmicort);
                                        editor.putInt("budesonide", answer_budesonide);
                                        editor.putInt("prednisone", answer_prednisone);
                                        editor.putInt("prednisolone", answer_prednisolone);
                                        editor.putInt("orapred", answer_orapred);
                                        editor.putInt("montekulast", answer_montekulast);
                                        editor.putInt("singulair", answer_singulair);
                                        editor.commit();

                                        //LoadPreferences();
                                        //Toast.makeText(getApplicationContext(),userN+userDO+userG+zip+oth_zip+ans_albuterol+answer_advair,Toast.LENGTH_LONG).show();

                                        addRowtoUsersTable(userN, userA, userG, zip, oth_zip, ans_albuterol, ans_ventolin, ans_proair, ans_xoponex, ans_atrovent, ans_combivent,
                                                ans_duoneb, answer_dulera, answer_symbicort, answer_advair, answer_flovent, answer_asmanex, answer_qvar, answer_pulmicort, answer_budesonide, answer_prednisone, answer_prednisolone,
                                                answer_orapred, answer_montekulast, answer_singulair);

                                        Toast.makeText(getApplicationContext(), userN + " Successfully added ", Toast.LENGTH_LONG).show();
                                        //asthmaApp.userLoggedIn = userN;
                                        // Set default alarm

                                        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        Intent savedAlarm = new Intent(getBaseContext(), AlarmReceiver.class);
                                        PendingIntent recurringAlarm = PendingIntent.getBroadcast(getBaseContext(), 0, savedAlarm, PendingIntent.FLAG_CANCEL_CURRENT);

                                        if (alarms != null) {
                                            Calendar updateTime = Calendar.getInstance();
                                            updateTime.set(Calendar.HOUR_OF_DAY, 20);
                                            updateTime.set(Calendar.MINUTE, 0);
                                            updateTime.set(Calendar.SECOND, 0);
                                            alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, recurringAlarm);
                                            Toast.makeText(
                                                    LongBronchodilators.this, "Alarm is Set", Toast.LENGTH_LONG).show();
                                        }


                                        Intent i = new Intent(LongBronchodilators.this, AdminActivity.class);

                                        startActivity(i);


                                    }

                                }
        );

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent j = new Intent(LongBronchodilators.this, ShortBronchodilators.class);
                j.putExtras(intent.getExtras());
                startActivity(j);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void LoadPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userN = sharedPreferences.getString("userName", "");
        userG = sharedPreferences.getString("userGender", "");
       // userDO = sharedPreferences.getString("dateOfBirth", "");
        userA = sharedPreferences.getInt("userAge", 0);
        zip = sharedPreferences.getInt("userZipcode", 0);
        oth_zip = sharedPreferences.getInt("userOtherZipCode", 0);
        ans_albuterol = sharedPreferences.getInt("albuterol", 0);
        ans_ventolin = sharedPreferences.getInt("ventolin", 0);
        ans_proair = sharedPreferences.getInt("proair", 0);
        ans_xoponex = sharedPreferences.getInt("xoponex", 0);
        ans_atrovent = sharedPreferences.getInt("atrovent", 0);
        ans_combivent = sharedPreferences.getInt("combivent", 0);
        ans_duoneb = sharedPreferences.getInt("duoneb", 0);
        //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

    }


}


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_long_acting_bronchodilators);
    }*/
//}
