package org.knoesis.health;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ObservationsActivity extends KHealthAsthmaParentActivity {
	Button btnNO;
	Button btnEnvironmental;
	Button btnGps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observations);
		//When accessing this activity, the user is not using the sequential option
		asthmaApp.setSequentialCheck(false);
		//the readings will be individual and not sequential so you tell the system
		asthmaApp.setIndividualReading(true);
		
		//btnNO = (Button) findViewById(R.id.observations_NO);
		
		btnNO.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	((AsthmaApplication) getApplication()).setSequentialCheck(false);
		    	//Made a change to the class name to point to the new ACTIVITY @DAN Vanuch 1/30/2015
		    	Intent intent=new Intent(ObservationsActivity.this, Observation_NODE_NEW.class);
				startActivity(intent);
		    }
		});
		
		btnEnvironmental = (Button) findViewById(R.id.observations_environmantal);
		btnEnvironmental.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    	
		    }
		});
		btnEnvironmental.setVisibility(View.GONE);
		btnGps = (Button) findViewById(R.id.gps);
		btnGps.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	((AsthmaApplication) getApplication()).setSequentialCheck(false);
		    	Intent intent=new Intent(ObservationsActivity.this,PopulationSignals.class);
				startActivity(intent);
		    }
		});
		//btnGps.setVisibility(View.GONE);
	}

	

}
