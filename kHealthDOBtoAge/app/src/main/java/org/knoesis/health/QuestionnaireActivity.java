package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.knoesis.health.constants.Constants;

public class QuestionnaireActivity extends KHealthAsthmaParentActivity {

	// Strings to store the answers
	int answer_cough;
	int answer_wake;
	int answer_wheeze;
	int answer_activity;
	int answer_rescue;
	String answer_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionnaire);
		// This tells the keyboard not to open on startup
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// This is the drop down Spinner object for the coughing question
		final Spinner coughLevel = (Spinner) findViewById(R.id.coughLevelSpinner);
		// This is the drop down Spinner object for the waking from sleep
		// question
		final Spinner wakeFromSleep = (Spinner) findViewById(R.id.wakeFromSleepSpinner);
		// This is the drop down Spinner object for the wheezing question
		final Spinner wheezing = (Spinner) findViewById(R.id.wheezingSpinner);
		// This is the drop down Spinner object for the activity question
		final Spinner activityLevel = (Spinner) findViewById(R.id.activtyLevelSpinner);
		// This is the drop down Spinner object for the rescue inhaler question
		final Spinner rescueAmount = (Spinner) findViewById(R.id.rescueNumber);
		// submit button

		Button submit = (Button) findViewById(R.id.submit);

		// This is the text box for the extra comments that a person can enter
		final EditText comments = (EditText) findViewById(R.id.comments);

		// This is the adapter to implement the correct string array with the
		// Spinner object
		// You then add the adapter to the Spinner object itself
		ArrayAdapter<String> coughAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.wake_with_cough);
		coughLevel.setAdapter(coughAdapter);
		ArrayAdapter<String> wakeAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.inhaler_at_night);
		wakeFromSleep.setAdapter(wakeAdapter);

		ArrayAdapter<String> wheezeAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.wheeze);
		wheezing.setAdapter(wheezeAdapter);

		ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.activity);
		activityLevel.setAdapter(activityAdapter);

		ArrayAdapter<String> rescueAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.rescueInhaler);
		rescueAmount.setAdapter(rescueAdapter);

		comments.setMovementMethod(new ScrollingMovementMethod());

		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// These are the strings that contain the answers from the drop
				// down menus
				answer_cough = coughLevel.getSelectedItemPosition();
				answer_wake = wakeFromSleep.getSelectedItemPosition();
				answer_wheeze = wheezing.getSelectedItemPosition();
				answer_activity = activityLevel.getSelectedItemPosition();
				answer_rescue = rescueAmount.getSelectedItemPosition();
				answer_comment = comments.getText().toString();
				// This is the method call to open the dialog box to see if the
				// user
				// wants to save the answers
				showAlertDialogBox();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.questionnaire, menu);

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


							/**	SharedPreferences.Editor editor = getSharedPreferences(
										Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
								SharedPreferences prefs_flag = getSharedPreferences(Constants.MY_PREFS_NAME,
										MODE_WORLD_READABLE);
								Log.d("testing flag", prefs_flag.getString("flag",null)+"");

								if(prefs_flag.getString("flag",null) != prefs_flag.getString("Last User logged In", null)+"flag1")
								{
									//call BG service
									Log.d("Inside if", "working");
									//set flag to 1
									editor.putString("flag", prefs_flag.getString("Last User logged In", null)+ "flag1");

								} **/

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
								// adding the wheez question answer
								addRowtoQuestionnaireTable(3,
										String.valueOf(answer_wheeze));
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
								// finally finishing the activity
								quickMessage("Successfully submitted");
								// ===================================
								QuestionnaireActivity.this.finish();
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
