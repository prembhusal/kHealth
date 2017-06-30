package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//Button sensorDroneOptions = (Button) findViewById(R.id.sensor_drone_connections);
		//Button nodeOptions = (Button) findViewById(R.id.node_connections);
		//Button createNewUser = (Button) findViewById(R.id.createNewUser);
		//Button changeUser = (Button) findViewById(R.id.changeUser);
		Button alarmSetup = (Button) findViewById(R.id.setupAlarm);
		asthmaApp.setSequentialCheck(false);
		asthmaApp.setIndividualReading(false);

		
	    alarmSetup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SettingsActivity.this, AlarmSetupActivity.class);
				startActivity(i);
			}
		});

	}

}
