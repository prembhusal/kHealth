package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.knoesis.health.constants.Constants;

public class Current_Questions_New extends KHealthAsthmaParentActivity {
    int answer_current_cough = 0;
    int answer_current_wheeze = 0;
    int answer_current_chest = 0;
    int answer_current_breath_HF = 0;
    int answer_current_NOW = 0;
    int answer_current_CTF = 0;
    String answer_current_other = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__questions__new);
        final CheckBox other = (CheckBox) findViewById(R.id.RB_Other);
        final CheckBox cough = (CheckBox) findViewById(R.id.RB_Cough);
        final CheckBox wheeze = (CheckBox) findViewById(R.id.RB_Wheeze);
        final CheckBox chest = (CheckBox) findViewById(R.id.RB_CT);
        final CheckBox breath = (CheckBox) findViewById(R.id.RB_HF);
        final CheckBox nose = (CheckBox) findViewById(R.id.RB_NOW);
        final CheckBox sentences = (CheckBox) findViewById(R.id.RB_CTF);
        final EditText et = (EditText) findViewById(R.id.other_comments);
        Button next = (Button) findViewById(R.id.current_Next);
        Button sub = (Button) findViewById(R.id.submitCQ);

        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (other.isChecked()) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    et.setVisibility(View.GONE);

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        String[] checkBoxNames =
                {"other", "cough", "wheeze", "chest", "breath", "nose", "sentences"};
        CheckBox[] checkBoxes =
                {other, cough, wheeze, chest, breath, nose, sentences};
        if (extras != null) {
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(extras.getBoolean(checkBoxNames[i]));
            }
            et.setText(extras.getString("other_comments", ""));
        }

        //if (Constants.CURRENT && !Constants.CURRENT_AND_NIGHT
        //		&& !Constants.CURRENT_AND_DAY) {
        // goes to sensordrone connections options then node connection
        // options
        next.setVisibility(View.GONE);
        sub.setVisibility(View.VISIBLE);

        //}

        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();
                String[] checkBoxNames =
                        {"other", "cough", "wheeze", "chest", "breath", "nose", "sentences"};
                CheckBox[] checkBoxes =
                        {other, cough, wheeze, chest, breath, nose, sentences};
                for (int i = 0; i < checkBoxes.length; i++) {
                    extras.putBoolean(checkBoxNames[i], checkBoxes[i].isChecked());
                }
                extras.putString("other_comments", et.getText().toString());

                Intent i = new Intent();
                i.putExtras(extras);
                if (Constants.CURRENT_AND_DAY && !Constants.CURRENT_AND_NIGHT) {
                    i.setClass(Current_Questions_New.this, Day_Question_Zero.class);
                    startActivity(i);
                    finish();
                } else if (Constants.CURRENT_AND_NIGHT
                        && !Constants.CURRENT_AND_DAY) {

                    i.setClass(Current_Questions_New.this, Night_Question_Zero.class);
                    startActivity(i);
                    finish();
                } else {

                    i.setClass(Current_Questions_New.this, Night_Question_Zero.class);
                    startActivity(i);
                    finish();
                }

                if (cough.isChecked()) {

                    answer_current_cough = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_cough(answer_current_cough);

                }
                if (wheeze.isChecked()) {

                    answer_current_wheeze = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_wheeze(answer_current_wheeze);
                }
                if (chest.isChecked()) {

                    answer_current_chest = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_chest(answer_current_chest);
                }
                if (breath.isChecked()) {

                    answer_current_breath_HF = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_breath_hard_fast(answer_current_breath_HF);
                }
                if (nose.isChecked()) {

                    answer_current_NOW = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_nose_opens_wide(answer_current_NOW);
                }
                if (sentences.isChecked()) {

                    answer_current_CTF = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_cant_talk_full_sentences(answer_current_CTF);

                }
                if (other.isChecked()) {

                    answer_current_other = et.getText().toString();
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_other(answer_current_other);
                }

            }
        });

        sub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cough.isChecked()) {

                    answer_current_cough = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_cough(answer_current_cough);

                }
                if (wheeze.isChecked()) {

                    answer_current_wheeze = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_wheeze(answer_current_wheeze);
                }
                if (chest.isChecked()) {

                    answer_current_chest = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_chest(answer_current_chest);
                }
                if (breath.isChecked()) {

                    answer_current_breath_HF = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_breath_hard_fast(answer_current_breath_HF);
                }
                if (nose.isChecked()) {

                    answer_current_NOW = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_nose_opens_wide(answer_current_NOW);
                }
                if (sentences.isChecked()) {

                    answer_current_CTF = 1;
                    ((AsthmaApplication) getApplication())
                            .setAnswer_cant_talk_full_sentences(answer_current_CTF);

                }
                if (other.isChecked()) {

                    answer_current_other = et.getText().toString();
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_other(answer_current_other);
                }

                if (other.isChecked()) {

                    answer_current_other = et.getText().toString();
                    ((AsthmaApplication) getApplication())
                            .setAnswer_current_other(answer_current_other);
                }
                Bundle extras = new Bundle();
                String[] checkBoxNames =
                        {"other", "cough", "wheeze", "chest", "breath", "nose", "sentences"};
                CheckBox[] checkBoxes =
                        {other, cough, wheeze, chest, breath, nose, sentences};
                for (int i = 0; i < checkBoxes.length; i++) {
                    extras.putBoolean(checkBoxNames[i], checkBoxes[i].isChecked());
                }
                extras.putString("other_comments", et.getText().toString());

                if (asthmaApp.activityChainIndex + 1 < asthmaApp.activityChain.size()) {
                    Intent i = new Intent(Current_Questions_New.this, MedicationUsage.class);
                    if (getIntent().getExtras() != null) i.putExtras(getIntent().getExtras());
                    i.putExtras(extras);
                    i.putExtra("CURRENT", true);
                    startActivity(i);
                    finish();
                } else {
                    showAlertDialogBox(extras);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.current__questions__new, menu);
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

    public void showAlertDialogBox(final Bundle extras) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you would like to submit?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                asthmaApp.setSequentialCheck(false);

                                addRowtoQuestionnaireTable(7,
                                        String.valueOf(answer_current_cough));
                                addRowtoQuestionnaireTable(8,
                                        String.valueOf(answer_current_wheeze));
                                addRowtoQuestionnaireTable(9,
                                        String.valueOf(answer_current_chest));
                                addRowtoQuestionnaireTable(10, String
                                        .valueOf(answer_current_breath_HF));
                                addRowtoQuestionnaireTable(11,
                                        String.valueOf(answer_current_NOW));
                                addRowtoQuestionnaireTable(12,
                                        String.valueOf(answer_current_CTF));

                                if (answer_current_other.length() != 0) {
                                    addRowtoQuestionnaireTable(13, String
                                            .valueOf(answer_current_other));
                                }

                                quickMessage("Successfully submitted");
                                // ===================================

                                Intent i;
                                asthmaApp.setSequentialCheck(true);
                                asthmaApp.setIndividualReading(false);
                                //i = new Intent(Current_Questions_New.this, HomeActivity.class);
                                i = new Intent(Current_Questions_New.this, MedicationUsage.class);
                                Bundle callingIntentExtras = getIntent().getExtras();
                                if (callingIntentExtras != null) {
                                    i.putExtras(getIntent().getExtras());
                                }
                                i.putExtras(extras);
                                i.putExtra("CURRENT", true);
                                //i = new Intent(Current_Questions_New.this, Observation_NODE_NEW.class);
                                startActivity(i);
                                finish();

                            }

                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
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
