package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GraphSelectionActivity extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_selection);
		//Button NOGraph = (Button) findViewById(R.id.no_graph);
		//Button COGraph=(Button)findViewById(R.id.co_graph);
		//Button temperatureGraph=(Button)findViewById(R.id.temp_graph);
		//Button humidityGraph=(Button)findViewById(R.id.humidity_graph);
		Button questionnaireGraph=(Button)findViewById(R.id.questionnaire);
		Button populaitonSignals=(Button)findViewById(R.id.PopulaitonSignals);
		
		
		
		//Creating the listener for NO observations button
		/*NOGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(GraphSelectionActivity.this,
						GraphActivity.class);
				//Adding the sensor position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.no.ordinal());
				startActivity(i);

				
			}
		}); */
		//Creating the listener for CO observations button
		/*COGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(GraphSelectionActivity.this,
						GraphActivity.class);
				//Adding the sensor position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.co.ordinal());
				startActivity(i);

				
			}
		});
		//Creating the listener for Temperature observations button
//		temperatureGraph.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(GraphSelectionActivity.this,
//						GraphActivity.class);
//				//Adding the sensor position in the enum of the graphs
//				i.putExtra("SensorName", Constants.graphs.temperature.ordinal());
//				startActivity(i);
//
//
//			}
//		});
		//Creating the listener for Humidity observations button
		humidityGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(GraphSelectionActivity.this,
						GraphActivity.class);
				//Adding the sensor position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.humidity.ordinal());
				startActivity(i);
		
				
			}
		});*/
		questionnaireGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if the questionnaire is selected then invoke the questionnaire selection activity.
				Intent i = new Intent(GraphSelectionActivity.this,
						QuestionnaireGraphSelection.class);
				startActivity(i);
		
				
			}
		});
		populaitonSignals.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if the populaitonSignals is selected then invoke the PopulationSignalsGraphSelection activity
				Intent i = new Intent(GraphSelectionActivity.this,
						PopulationSignalsGraphSelection.class);
				startActivity(i);
		
				
			}
		});
		
		
	}//End of the Oncreate method

	

}
