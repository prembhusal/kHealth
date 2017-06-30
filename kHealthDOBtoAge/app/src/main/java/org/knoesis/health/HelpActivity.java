package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelpActivity extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		Button sensorPhotos = (Button) findViewById(R.id.sensorPhotos);
		sensorPhotos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HelpActivity.this, SensorPhotosActivity.class);
				startActivity(i);
				
				
			}
		});
	}

public void onClickHelpConnect (View v) {
		
	AlertInfo.connectionInfo(this);
	}


}
