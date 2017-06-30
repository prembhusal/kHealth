package org.knoesis.health;

import org.knoesis.health.constants.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class QuestionnaireGraphSelection extends KHealthAsthmaParentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionnaire_graph_selection);
		Button coughGraph = (Button) findViewById(R.id.cough_graph);
		Button sleepGraph=(Button)findViewById(R.id.sleep_graph);
		Button wheezingGraph=(Button)findViewById(R.id.wheezing_graph);
		Button activityGraph=(Button)findViewById(R.id.activity_graph);
		Button inhalerUsageGraph=(Button)findViewById(R.id.inhaler_usage_graph);
		Button commentsDisplay=(Button)findViewById(R.id.comments_display);
		
		
		//Creating the listener for cough answers button
		coughGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuestionnaireGraphSelection.this,
						GraphActivity.class);
				//Adding the cough position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.cough.ordinal());
				startActivity(i);

				
			}
		});
		//Creating the listener for sleep activity answers button
		sleepGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuestionnaireGraphSelection.this,
						GraphActivity.class);
				//Adding the sleep graph position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.sleep.ordinal());
				startActivity(i);

				
			}
		});
		//Creating the listener for wheezing answers button
		wheezingGraph.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuestionnaireGraphSelection.this,
						GraphActivity.class);
				//Adding the Wheezing graph position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.wheezing.ordinal());
				startActivity(i);
		
				
			}
		});
		//Creating the listener for activity answers button
		activityGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuestionnaireGraphSelection.this,
						GraphActivity.class);
				//Adding the Activity graph position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.activity.ordinal());
				startActivity(i);
		
				
			}
		});
		
		//Creating the listener for Inhaler Usage answers button
		inhalerUsageGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if the inhaler usage question is selected then invoke the Graph activity with it's enum value.
				Intent i = new Intent(QuestionnaireGraphSelection.this,
						GraphActivity.class);
				//Adding the sensor position in the enum of the graphs
				i.putExtra("SensorName", Constants.graphs.inhalerusage.ordinal());
				
				startActivity(i);
		
				
			}
		});
		//Creating the listener for comments button
		commentsDisplay.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//if the comments is selected then invoke the comments display activity.
						Intent i = new Intent(QuestionnaireGraphSelection.this,
								CommentsDisplay.class);
						startActivity(i);
				
						
					}
				});		
	}

	

}
