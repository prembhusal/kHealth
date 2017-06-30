package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.knoesis.health.constants.Constants;

public class DayQuestionThreeActivity extends KHealthAsthmaParentActivity {
	
	int answer_cough;
	int answer_wake;
	int answer_wheezeYesNo;
	int answer_activity;
	int answer_rescue;
	int answer_wheeze_amount;
	String answer_comment;
	int answer_current_cough = 0;
	int answer_current_wheeze = 0;
	int answer_current_chest = 0;
	int answer_current_breath_HF = 0;
	int answer_current_NOW = 0;
	int answer_current_CTF = 0;
	String answer_current_other = "";
	int answer_albuter_in_30 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_question_three);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		TextView limitActivityTxtVw = (TextView) findViewById(R.id.limitActivity);
		String user = asthmaApp.getCurrentLoggedInUser();
		limitActivityTxtVw.setText("How much did asthma symptoms limit " + user + "'s activity?");
		
		final Spinner limitActivity = (Spinner) findViewById(R.id.limitActivitySpinner);
		
		ArrayAdapter<String> limitActivityAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.limitActivtyValues);
		limitActivity.setAdapter(limitActivityAdapter);
		
		Button submit = (Button) findViewById(R.id.submit);
		Button previousQuestion = (Button) findViewById(R.id.previousQuestion);
		
		// This is the text box for the extra comments that a person can enter
		final EditText commentET = (EditText) findViewById(R.id.comments);
		commentET.setMovementMethod(new ScrollingMovementMethod());

		Bundle extras = getIntent().getExtras();
		if(extras != null){
			limitActivity.setSelection(extras.getInt("activity", 0));
			commentET.setText(extras.getString("day_comment", ""));
		}

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//answer_cough = ((AsthmaApplication) getApplication()).getAnswerToCoughLastNight();
				//answer_wake = ((AsthmaApplication) getApplication()).getAnswerToWakeLastNight();
				answer_rescue = ((AsthmaApplication) getApplication()).getNumberOfTimesInahlerUsed();
				answer_wheezeYesNo = ((AsthmaApplication) getApplication()).getWheezeChoice();
				answer_wheeze_amount = ((AsthmaApplication) getApplication()).getWheezeAmount();
				answer_activity = limitActivity.getSelectedItemPosition();
				answer_comment = commentET.getText().toString();
				answer_current_cough = ((AsthmaApplication) getApplication()).getAnswer_current_cough();
				answer_current_wheeze = ((AsthmaApplication) getApplication()).getAnswer_current_wheeze();
				answer_current_chest = ((AsthmaApplication) getApplication()).getAnswer_current_chest();
				answer_current_breath_HF = ((AsthmaApplication) getApplication()).getAnswer_current_breath_hard_fast();
				answer_current_NOW = ((AsthmaApplication) getApplication()).getAnswer_current_nose_opens_wide();
				answer_current_CTF = ((AsthmaApplication) getApplication()).getAnswer_cant_talk_full_sentences();
				answer_current_other = ((AsthmaApplication) getApplication()).getAnswer_current_other();
				answer_albuter_in_30 = 	((AsthmaApplication) getApplication()).getAnswer_albuterol_in_30();

				showAlertDialogBox();
			}
		});
		
		previousQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(DayQuestionThreeActivity.this, DayQuestionTwoActivity.class);
				i.putExtras(getIntent().getExtras());
				i.putExtra("DAY", true);
				i.putExtra("activity", limitActivity.getSelectedItemPosition());
				i.putExtra("day_comment", commentET.getText().toString());
				startActivity(i);
				finish();

			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_question_three, menu);
		return true;
	}
	
	public void showAlertDialogBox() {

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
								// if this button is clicked, close
								// current activity
								// ===================================
								// Enter code here to save the values to the
								// database
								
								/*if(Constants.CURRENT_AND_DAY && Constants.CURRENT_AND_NIGHT){
								// adding the cough question answer
								addRowtoQuestionnaireTable(1,
										String.valueOf(answer_cough));
								// adding the wake question answer
								addRowtoQuestionnaireTable(2,
										String.valueOf(answer_wake));
								}
								// adding the wheeze question answer
								
								//Finds the answer that they had to the the third day question to see if they said yes or no
								int check = ((AsthmaApplication) getApplication()).getWheezeChoice();
								//if they said yes, add the value of there answer 
								if(check == 1){
								addRowtoQuestionnaireTable(3,
										String.valueOf(answer_wheeze_amount));
								}else {
									addRowtoQuestionnaireTable(3,
											String.valueOf(0));
								}
								// adding the activity question answer
								addRowtoQuestionnaireTable(4,
										String.valueOf(answer_activity));
								// adding the rescue inhaler question answer
								addRowtoQuestionnaireTable(5,
										String.valueOf(answer_rescue));
								// adding the comment question answer if it is
								// not empty
								if (answer_comment.length() != 0) {
									// If there is something in the comments
									// then only store it in database.
									addRowtoQuestionnaireTable(6,
											answer_comment);
								}
								addRowtoQuestionnaireTable(7, String
										.valueOf(answer_current_cough));
								addRowtoQuestionnaireTable(8, String
										.valueOf(answer_current_wheeze));
								addRowtoQuestionnaireTable(9, String
										.valueOf(answer_current_chest));
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

								addRowtoQuestionnaireTable(14,
										String.valueOf(answer_albuter_in_30));*/

                                Bundle extras = getIntent().getExtras();
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        COUGH_LEVEL.ordinal(),
										String.valueOf(extras.getInt("coughLevel", 0)));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        WAKE_FROM_SLEEP.ordinal(),
                                        String.valueOf(extras.getInt("wakeFromSleep", 0)));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        TIGHTNESS.ordinal(),
										String.valueOf(extras.getInt("tightness", 0)));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        ACTIVITY.ordinal(),
										String.valueOf(answer_activity));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        TAKE_ALBUTEROL.ordinal(),
                                        String.valueOf(extras.getInt("takeAlbuterol", 0)));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        COMMENT.ordinal(),
										answer_comment);
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        COUGH.ordinal(),
										String.valueOf(extras.getBoolean("cough", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        WHEEZE.ordinal(),
										String.valueOf(extras.getBoolean("wheeze", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        CHEST.ordinal(),
										String.valueOf(extras.getBoolean("chest", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        BREATH.ordinal(),
										String.valueOf(extras.getBoolean("breath", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        NOSE.ordinal(),
										String.valueOf(extras.getBoolean("nose", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        SENTENCES.ordinal(),
										String.valueOf(extras.getBoolean("sentences", false) ? 1 : 0));
                                addRowtoQuestionnaireTable(Constants.questionnaireIndicies.
                                        OTHER.ordinal(),
										extras.getString("other_comments", ""));

								// finally finishing the activity
								quickMessage("Successfully submitted");
								// ===================================
								
																
								Intent i;
								asthmaApp.setSequentialCheck(true);
								asthmaApp.setIndividualReading(false);
								i = new Intent(DayQuestionThreeActivity.this, MedicationUsage.class);
								i.putExtras(getIntent().getExtras());
								i.putExtra("NIGHT", false);
								i.putExtra("CURRENT", false);
								i.putExtra("DAY", true);

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
