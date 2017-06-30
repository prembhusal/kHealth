package org.knoesis.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Swati on 7/10/2016.
 */

public class ShortBronchodilators extends KHealthAsthmaAdminActivity {

    int answer_albuterol = 0;
    int answer_ventolin = 0;
    int answer_proair = 0;
    int answer_xoponex = 0;
    int answer_atrovent = 0;
    int answer_combivent = 0;
    int answer_duoneb = 0;

    String userN;
    String userG;
    String userDO;
    int zip;
    int other_zip ;

    final Context context = this;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_short_acting_bronchodilators);
        final CheckBox albuterol = (CheckBox)  findViewById(R.id.cb1);
        final CheckBox ventolin = (CheckBox) findViewById(R.id.cb2);
        final CheckBox proair = (CheckBox) findViewById(R.id.cb3);
        final CheckBox xoponex = (CheckBox) findViewById(R.id.cb4);
        final CheckBox atrovent = (CheckBox) findViewById(R.id.cb5);
        final CheckBox combivent = (CheckBox) findViewById(R.id.cb6);
        final CheckBox duoneb = (CheckBox) findViewById(R.id.cb7);
        Button NEXT2 = (Button) findViewById(R.id.options_next2);
        Button previousQuestion =  (Button) findViewById(R.id.previousQuestion);

        final Intent intent = getIntent();
        userN = intent.getStringExtra("name");
        userDO = intent.getStringExtra("dateOfBirth");
        userG = intent.getStringExtra("gender");
        zip = intent.getIntExtra("zipCode", 0);
        other_zip = intent.getIntExtra("otherZipCode", 0);

        NEXT2.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v) {

                if(albuterol.isChecked())
                {
                    answer_albuterol=1;
                }
                if(ventolin.isChecked())
                {
                    answer_ventolin=1;
                }
                if(proair.isChecked())
                {
                    answer_proair=1;
                }
                if(xoponex.isChecked())
                {
                    answer_xoponex=1;
                }
                if(atrovent.isChecked())
                {
                    answer_atrovent=1;
                }
                if(combivent.isChecked())
                {
                    answer_combivent=1;
                }
                if(duoneb.isChecked())
                {
                    answer_duoneb=1;
                }
                //Using shared preferences for the parameters entered in short_acting_bronchodilators
                SharedPreferences prefs = getSharedPreferences(userN+"Prefs", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("albuterol", answer_albuterol);//SavePreferencesInt("albuterol",answer_albuterol);
                editor.putInt("ventolin", answer_ventolin);//SavePreferencesInt("ventolin",answer_ventolin);
                editor.putInt("proair", answer_proair);//SavePreferencesInt("proair",answer_proair);
                editor.putInt("xoponex", answer_xoponex);//SavePreferencesInt("xoponex",answer_xoponex);
                editor.putInt("atrovent", answer_atrovent);//SavePreferencesInt("atrovent",answer_atrovent);
                editor.putInt("combivent", answer_combivent);//SavePreferencesInt("combivent",answer_combivent);
                editor.putInt("duoneb", answer_duoneb);//SavePreferencesInt("duoneb",answer_duoneb);
                editor.commit();
                Intent i = new Intent(ShortBronchodilators.this, LongBronchodilators.class);
                Bundle bundle = new Bundle();
                bundle.putAll(intent.getExtras());
                bundle.putInt("albuterol", answer_albuterol);
                bundle.putInt("ventolin", answer_ventolin);
                bundle.putInt("proair", answer_proair);
                bundle.putInt("xoponex", answer_xoponex);
                bundle.putInt("atrovent", answer_atrovent);
                bundle.putInt("combivent", answer_combivent);
                bundle.putInt("duoneb", answer_duoneb);
                i.putExtras(bundle);
                startActivity(i);

                 }

                                 }
        );

        previousQuestion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent j = new Intent(ShortBronchodilators.this, CreateUser.class);
                j.putExtras(intent.getExtras());
                startActivity(j);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void SavePreferencesInt(String key, Integer value){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    }
