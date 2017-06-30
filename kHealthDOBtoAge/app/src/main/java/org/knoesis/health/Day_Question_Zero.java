package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class Day_Question_Zero extends KHealthAsthmaParentActivity {
	int previousDay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day__question__zero);

		TextView fQ = (TextView) findViewById(R.id.FirstQues1);
		fQ.setText("Are you answering for today or yesterday?");
		final RadioButton yesterday = (RadioButton) findViewById(R.id.yesterday);
		final RadioButton  today = (RadioButton) findViewById(R.id.today);
		Button nextQuestion = (Button) findViewById(R.id.nextQues1);
		Button previousQuestion= (Button) findViewById(R.id.previousQues);

		
		yesterday.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(yesterday.isChecked() == true){
					today.setChecked(false);
					previousDay = 1;
				}
			}
		});
		
		today.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(today.isChecked() == true){
					yesterday.setChecked(false);
					previousDay = 0;
					
				}
			}
		});
		nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				((AsthmaApplication) getApplication()).setAnswer_yesterday(previousDay);
				Intent i = new Intent(Day_Question_Zero.this, DayQuestionOneActivity.class);
				if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
				startActivity(i);
                finish();
			}
		});

        previousQuestion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

				for(int i = 0; i < asthmaApp.activityChain.size(); i++){
					System.out.println(asthmaApp.activityChain.get(i).getSimpleName());
				}
				System.out.println(asthmaApp.activityChainIndex);
                ((AsthmaApplication) getApplication()).setAnswer_yesterday(previousDay);
                Intent i = new Intent(Day_Question_Zero.this, asthmaApp.activityChain.
                        get(asthmaApp.activityChainIndex - 1));
                asthmaApp.activityChainIndex--;
				if(getIntent().getExtras() != null) {i.putExtras(getIntent().getExtras());}
                startActivity(i);
                finish();
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day__question__zero, menu);

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
}
