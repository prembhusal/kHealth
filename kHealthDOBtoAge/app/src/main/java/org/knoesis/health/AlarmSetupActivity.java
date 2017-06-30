package org.knoesis.health;

import java.util.Calendar;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmSetupActivity extends KHealthAsthmaParentActivity {

	AlarmManager alarms;
	PendingIntent recurringAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_setup);
		alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent savedAlarm = new Intent(getBaseContext(),
				AlarmReceiver.class);
		
		recurringAlarm = PendingIntent.getBroadcast(
				getBaseContext(), 0, savedAlarm,
				PendingIntent.FLAG_CANCEL_CURRENT);
		final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
		Button setAlarm = (Button) findViewById(R.id.setAlarm);
		Button cancelAlarm = (Button) findViewById(R.id.cancelAlarm);
		// final CheckBox vibrate = (CheckBox) findViewById(R.id.vibrate);
		// final CheckBox sound = (CheckBox) findViewById(R.id.sound);

		cancelAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alarms != null) {
					alarms.cancel(recurringAlarm);
					Toast.makeText(AlarmSetupActivity.this, "Alarm Canceled",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		setAlarm.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(alarms != null){
					// Gets the time that the user entered
					int currentHour = timePicker.getCurrentHour();
					int currentMinute = timePicker.getCurrentMinute();
	
					Calendar updateTime = Calendar.getInstance();
//					updateTime.setTimeZone(TimeZone.getTimeZone("EST"));
					updateTime.set(Calendar.HOUR_OF_DAY, currentHour);
					updateTime.set(Calendar.MINUTE, currentMinute);
					updateTime.set(Calendar.SECOND, 0);
	
					alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
							updateTime.getTimeInMillis(),
							AlarmManager.INTERVAL_DAY, recurringAlarm);
	
					Toast.makeText(
						AlarmSetupActivity.this, "Alarm is Set", Toast.LENGTH_LONG).show();

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_setup, menu);
		return true;
	}

}
