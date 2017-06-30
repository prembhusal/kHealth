package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.knoesis.health.constants.Constants;

public class NightQuestionsActivity extends KHealthAsthmaParentActivity {
	int answer_cough;
	int answer_wake;
	int answer_current_cough = 0;
	int answer_current_wheeze = 0;
	int answer_current_chest = 0;
	int answer_current_breath_HF = 0;
	int answer_current_NOW = 0;
	int answer_current_CTF = 0;
	String answer_current_other = "";
	int answer_albuterol = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_night_questions);

		// This is the drop down Spinner object for the coughing question
		final Spinner coughLevel = (Spinner) findViewById(R.id.coughLevelSpinner);
		// This is the drop down Spinner object for the waking from sleep
		// question
		final Spinner wakeFromSleep = (Spinner) findViewById(R.id.takeAlbuterolNightSpinner);

		TextView coughLevelTxtVw = (TextView) findViewById(R.id.coughLevel);
		String user = asthmaApp.getCurrentLoggedInUser();
		coughLevelTxtVw.setText("Did " + user
				+ " wake up with cough or wheezing during the night");

		TextView takeAlbuterolTxtVw = (TextView) findViewById(R.id.takeAlbuterolNight);
		takeAlbuterolTxtVw.setText("Did " + user
				+ " take albuterol during the night due to cough or wheeze");

		// This is the adapter that will show the constants for the question
		ArrayAdapter<String> coughAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.wake_with_cough);
		coughLevel.setAdapter(coughAdapter);

		// This is the adapter that will show the constants for the question
		ArrayAdapter<String> wakeAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.inhaler_at_night);
		wakeFromSleep.setAdapter(wakeAdapter);

		// Button submit
		Button submitButton = (Button) findViewById(R.id.submitNQ);
		Button dayQuestion = (Button) findViewById(R.id.dayQuestions);

		Bundle extras = getIntent().getExtras();
        if(extras != null){
            coughLevel.setSelection(extras.getInt("coughLevel", 0));
            wakeFromSleep.setSelection(extras.getInt("wakeFromSleep", 0));

        }

		if(Constants.CURRENT_AND_DAY && Constants.CURRENT_AND_NIGHT){
			
			submitButton.setVisibility(View.GONE);
			dayQuestion.setVisibility(View.VISIBLE);
			
		}
		
		dayQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(NightQuestionsActivity.this, DayQuestionOneActivity.class);
				startActivity(i);			
				((AsthmaApplication) getApplication()).setAnswerToCoughLastNight(answer_cough);
				((AsthmaApplication) getApplication()).setAnswerToWakeLastNight(answer_wake);
				finish();
				
			}
		});

		submitButton.setOnClickListener(new OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// Gets the answers that the user submitted
                Bundle extras = new Bundle();
				extras.putInt("coughLevel", coughLevel.getSelectedItemPosition());
			    extras.putInt("wakeFromSleep", wakeFromSleep.getSelectedItemPosition());

				/*// Store the answer in the application for future use
				((AsthmaApplication) getApplication())
						.setAnswerToCoughLastNight(answer_cough);
				// store the answer in the application for future use
				((AsthmaApplication) getApplication())
						.setAnswerToWakeLastNight(answer_wake);
				answer_cough = ((AsthmaApplication) getApplication()).getAnswerToCoughLastNight();
				answer_wake = ((AsthmaApplication) getApplication()).getAnswerToWakeLastNight();
				answer_current_cough = ((AsthmaApplication) getApplication()).getAnswer_current_cough();
				answer_current_wheeze = ((AsthmaApplication) getApplication()).getAnswer_current_wheeze();
				answer_current_chest = ((AsthmaApplication) getApplication()).getAnswer_current_chest();
				answer_current_breath_HF = ((AsthmaApplication) getApplication()).getAnswer_current_breath_hard_fast();
				answer_current_NOW = ((AsthmaApplication) getApplication()).getAnswer_current_nose_opens_wide();
				answer_current_CTF = ((AsthmaApplication) getApplication()).getAnswer_cant_talk_full_sentences();
				answer_current_other = ((AsthmaApplication) getApplication()).getAnswer_current_other();
				answer_albuterol= 	((AsthmaApplication) getApplication()).getAnswer_albuterol_in_30();
*/
                Intent i;
                asthmaApp.setSequentialCheck(true);
                asthmaApp.setIndividualReading(false);
				if(asthmaApp.activityChainIndex + 1 < asthmaApp.activityChain.size()) {
					i = new Intent(NightQuestionsActivity.this, MedicationUsage.class);
					if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
					i.putExtras(extras);
					i.putExtra("CURRENT", false);
					i.putExtra("DAY", false);
					i.putExtra("NIGHT", true);
					startActivity(i);
					finish();
				}else {
					showAlertDialogBox(extras);
				}
			}
		});

		Button previousEnvironmental = (Button) findViewById(R.id.previousCurrentSymptoms);

		previousEnvironmental.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.night_questions, menu);
		return true;
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
								// if this button is clicked, close
								// current activity
								// ===================================
								// Enter code here to save the values to the
								// database
								// adding the cough question answer
								addRowtoQuestionnaireTable(1,
										String.valueOf(answer_cough));
								// adding the wake question answer
								addRowtoQuestionnaireTable(2,
										String.valueOf(answer_wake));
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

								addRowtoQuestionnaireTable(14,
										String.valueOf(answer_albuterol));
								

								// finally finishing the activity
								quickMessage("Successfully submitted");
								// ===================================
								Intent i;
								asthmaApp.setSequentialCheck(true);
								asthmaApp.setIndividualReading(false);
								i = new Intent(NightQuestionsActivity.this, MedicationUsage.class);
								if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
                                i.putExtras(extras);
								i.putExtra("NIGHT", true);
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
