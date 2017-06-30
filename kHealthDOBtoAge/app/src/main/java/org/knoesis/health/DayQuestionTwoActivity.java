package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.knoesis.health.constants.Constants;

public class DayQuestionTwoActivity extends KHealthAsthmaParentActivity {

	int wheezeYesNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_question_two);

		final TextView wheeze = (TextView) findViewById(R.id.FirstQues1);
		String user = asthmaApp.getCurrentLoggedInUser();
		wheeze.setText("Has " + user + " had wheeze, chest tightness, or asthma related cough during the day?");
		
		// Radio buttons for the user to answer the question
		final RadioButton yesCheck = (RadioButton) findViewById(R.id.yesterday);
		final RadioButton noCheck = (RadioButton) findViewById(R.id.today);
		
		// Spinner with the options if they answer "Yes" for the question
		final Spinner tightnessDaySpinner = (Spinner) findViewById(R.id.tightnessDaySpinner);
		final TextView howOften = (TextView) findViewById(R.id.howOften);
		yesCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(yesCheck.isChecked() == true){
					noCheck.setChecked(false);
					wheezeYesNo = 1;
					tightnessDaySpinner.setVisibility(View.VISIBLE);
					howOften.setVisibility(View.VISIBLE);
				}
			}
		});
		
		noCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(noCheck.isChecked() == true){
					yesCheck.setChecked(false);
					wheezeYesNo = 0;
					tightnessDaySpinner.setVisibility(View.GONE);
					howOften.setVisibility(View.GONE);
				}
			}
		});

		
		
		ArrayAdapter<String> tightnessDayAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.wheeze);
		tightnessDaySpinner.setAdapter(tightnessDayAdapter);
		// The buttons to move to the next question and a button to move to the
		// previous question
		Button nextQuestion = (Button) findViewById(R.id.submitNQ);
		Button previousQuestion = (Button) findViewById(R.id.previousQuestion);

		Bundle extras = getIntent().getExtras();
        if(extras != null){
            wheezeYesNo = extras.getInt("wheezeYesNo", 0);
            tightnessDaySpinner.setSelection(extras.getInt("tightness", 0));
            if(wheezeYesNo == 1){
                yesCheck.setChecked(true);
                noCheck.setChecked(false);
            }else{
                yesCheck.setChecked(false);
                noCheck.setChecked(true);
            }
        }

		previousQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

                Intent i = new Intent(DayQuestionTwoActivity.this, DayQuestionOneActivity.class);
                i.putExtras(getIntent().getExtras());
                i.putExtra("wheezeYesNo", wheezeYesNo);
                i.putExtra("tightness", tightnessDaySpinner.getSelectedItemPosition());
                startActivity(i);
                finish();

			}
		});
		nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int tightnessChest = tightnessDaySpinner.getSelectedItemPosition();
			    ((AsthmaApplication) getApplication()).setWheezeAmount(tightnessChest);
			    ((AsthmaApplication) getApplication()).setWheezeChoice(wheezeYesNo);
				Intent i = new Intent(DayQuestionTwoActivity.this, DayQuestionThreeActivity.class);
                i.putExtras(getIntent().getExtras());
                i.putExtra("wheezeYesNo", wheezeYesNo);
                i.putExtra("tightness", tightnessDaySpinner.getSelectedItemPosition());
				startActivity(i);
                finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_question_two, menu);
		return true;
	}

}
