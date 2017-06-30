package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.knoesis.health.constants.Constants;

public class PopulationSignalsGraphSelection extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_population_signals_graph_selection);
		Button pollenLevelGraph = (Button) findViewById(R.id.pollen_level_graph);
		Button airQualityIndexGraph=(Button)findViewById(R.id.air_quality_index_graph);
		Button outdoorTemperatureGraph=(Button)findViewById(R.id.outdoor_temperature_graph);
		Button outdoorHumidityGraph=(Button)findViewById(R.id.outdoor_humidity_graph);
	
		
		
		//Creating the listener for pollen level graph
		pollenLevelGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PopulationSignalsGraphSelection.this,
						GraphActivity.class);
				//Adding the pollen level position in the enum of the sensors
				i.putExtra("SensorName", Constants.graphs.pollenLevel.ordinal());
				startActivity(i);

				
			}
		});
		//Creating the listener for AQI graph
		airQualityIndexGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PopulationSignalsGraphSelection.this,
						GraphActivity.class);
				//Adding the AQI graph position in the enum of the sensors
				i.putExtra("SensorName", Constants.graphs.AQI.ordinal());
				startActivity(i);

				
			}
		});
		//Creating the listener for outdoor temperature graph
		outdoorTemperatureGraph.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PopulationSignalsGraphSelection.this,
						GraphActivity.class);
				//Adding the outdoor temperature graph position in the enum of the sensors
				i.putExtra("SensorName", Constants.graphs.outdoorTemperature.ordinal());
				startActivity(i);
		
				
			}
		});
		//Creating the listener for outdoor humidity graph
		outdoorHumidityGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PopulationSignalsGraphSelection.this,
						GraphActivity.class);
				//Adding the outdoor humidity graph position in the enum of the sensors
				i.putExtra("SensorName", Constants.graphs.outdoorHumidity.ordinal());
				startActivity(i);
		
				
			}
		});
		
		
	}

}
