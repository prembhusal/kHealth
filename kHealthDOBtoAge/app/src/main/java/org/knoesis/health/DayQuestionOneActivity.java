package org.knoesis.health;

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

public class DayQuestionOneActivity extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_question_one);
		
		TextView inhalerUsedTxtVw = (TextView) findViewById(R.id.inhalerUsedAmount);
		String user = asthmaApp.getCurrentLoggedInUser();
		inhalerUsedTxtVw.setText("How many times did " + user + " take albuterol inhaler during the day for asthma symptoms?");		

		final Spinner takeAlbuterol = (Spinner) findViewById(R.id.albuterolAnswer);
		
		ArrayAdapter<String> coughAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, Constants.rescueInhaler);
		takeAlbuterol.setAdapter(coughAdapter);
		
		Button nextQuestion = (Button) findViewById(R.id.submitNQ);
		Button previousQuestion = (Button) findViewById(R.id.previousQuestion);

		Bundle extras = getIntent().getExtras();
        if(extras != null){
            takeAlbuterol.setSelection(extras.getInt("takeAlbuterol", 0));
        }

		nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int albuterol = takeAlbuterol.getSelectedItemPosition();
				((AsthmaApplication) getApplication()).setNumberOfTimesInhalerUsed(albuterol);
				Intent i = new Intent(DayQuestionOneActivity.this, DayQuestionTwoActivity.class);
				if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
                i.putExtra("takeAlbuterol", albuterol);
				startActivity(i);
                finish();
			}
		});
		
		previousQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

                Intent i = new Intent();
				if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
                i.putExtra("takeAlbuterol", takeAlbuterol.getSelectedItemPosition());
                Class prevActivity = asthmaApp.activityChain.get(asthmaApp.activityChainIndex - 1);
                asthmaApp.activityChainIndex--;
                i.setClass(DayQuestionOneActivity.this, prevActivity);
                startActivity(i);
                finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_question_one, menu);
		return true;
	}

}
