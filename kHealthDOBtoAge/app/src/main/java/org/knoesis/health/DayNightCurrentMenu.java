package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import org.knoesis.health.constants.Constants;

public class DayNightCurrentMenu extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_night_current_menu);

		final CheckBox day = (CheckBox) findViewById(R.id.Day);
		final CheckBox night = (CheckBox) findViewById(R.id.Night);
		final CheckBox current = (CheckBox) findViewById(R.id.Current);
		Button question = (Button) findViewById(R.id.day_night_current_next);

		
		Constants.CURRENT = false;
		Constants.CURRENT_AND_DAY = false;
		Constants.CURRENT_AND_NIGHT = false;
		Constants.NIGHT_DAY = false;
		
		
		question.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				asthmaApp.activityChain.clear();
				asthmaApp.activityChain.add(Current_Questions_New.class);
				if(night.isChecked() && !asthmaApp.activityChain.contains(Night_Question_Zero.class)){
					asthmaApp.activityChain.add(Night_Question_Zero.class);
				}
				if(day.isChecked() && !asthmaApp.activityChain.contains(Day_Question_Zero.class)){
					asthmaApp.activityChain.add(Day_Question_Zero.class);
				}
				if(!asthmaApp.activityChain.isEmpty()) {
					Class nextClass = asthmaApp.activityChain.get(0);
					asthmaApp.activityChainIndex = 0;
					Intent i = new Intent(DayNightCurrentMenu.this, nextClass);
					startActivity(i);
					finish();
				}
				/*if(current.isChecked() && !night.isChecked() && !day.isChecked()){
				Constants.CURRENT = true;
				Constants.CURRENT_AND_DAY = false;
				Constants.CURRENT_AND_NIGHT = false;
				Constants.NIGHT_DAY = false;
				Intent i = new Intent(DayNightCurrentMenu.this,
						Current_Questions_New.class);
				startActivity(i);
				
				}
				
				else if(day.isChecked() && !night.isChecked()){
					Constants.CURRENT_AND_DAY = true;
					Constants.CURRENT = false;
					Constants.CURRENT_AND_NIGHT = false;
					Constants.NIGHT_DAY = false;
					
					Intent i = new Intent(DayNightCurrentMenu.this,
							Current_Questions_New.class);
					startActivity(i);
					
					
					
				}
				else if(night.isChecked() && !day.isChecked()){
					
					Constants.CURRENT_AND_NIGHT = true;
					Constants.CURRENT_AND_DAY = false;
					Constants.CURRENT = false;
					Constants.NIGHT_DAY = false;
					
					Intent i = new Intent(DayNightCurrentMenu.this,
							Current_Questions_New.class);
					startActivity(i);
					
				}
				else if(night.isChecked() && day.isChecked()){
					
					Constants.CURRENT_AND_NIGHT = true;
					Constants.CURRENT_AND_DAY = true;
					Constants.CURRENT = false;
					Constants.NIGHT_DAY = false;
					Intent i = new Intent(DayNightCurrentMenu.this,
							Current_Questions_New.class);
					startActivity(i);
					
					
				}
				else {
					Constants.NIGHT_DAY = true;
					Constants.CURRENT_AND_DAY = false;
					Constants.CURRENT_AND_NIGHT = false;
					Constants.CURRENT = false;
					Intent i = new Intent(DayNightCurrentMenu.this,
							Current_Questions_New.class);
					startActivity(i);
										
				}*/
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_night_current_menu, menu);
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
