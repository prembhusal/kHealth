package org.knoesis.health;


import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class AnalyticsActivity extends KHealthAsthmaParentActivity {
	private String graphOptions[] = { "Nitric Oxide", "Carbon Monoxide",
			"Temperature", "Humidity" };
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private int startDate;
	private int startMonth;
	private int startYear;
	private int endDate;
	private int endMonth;
	private int endYear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analytics);

		final Spinner graphOpt = (Spinner) findViewById(R.id.selectObservations);
		startDatePicker = (DatePicker) findViewById(R.id.start_date);
		endDatePicker = (DatePicker) findViewById(R.id.end_date);

		ArrayAdapter<String> graphAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_row, graphOptions);
		graphOpt.setAdapter(graphAdapter);

		//Button to run the analysis
		Button runAnalysis = (Button) findViewById(R.id.run_analysis);
		//Button to run the correlation
		Button runCorrelation = (Button) findViewById(R.id.run_correlation);
		//OnClick listener for running the analysis
		runAnalysis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkDates()) {
					quickMessage("The dates were in order\nStart Date: " + (startMonth + 1) + "/" + startDate + "/" + startYear + "\n" + "End Date: " + (endMonth + 1) + "/" + endDate + "/" + endYear + "\n");

				} else {
					quickMessage("The dates were not in order");
				}

			}
		});
		
		//OnClick listener for running a correlation
		runCorrelation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Here is where the correlation will be ran
				
			}
		});
	}

	private Boolean checkDates() {
		Boolean check = false;
		//gets all of the values of the starting day, month and year from the starting DatePicker
		startDate = startDatePicker.getDayOfMonth();
		startMonth = startDatePicker.getMonth();
		startYear = startDatePicker.getYear();
		
		//gets all of the values of the ending day, month and year from the ending DatePicker
		endDate = endDatePicker.getDayOfMonth();
		endMonth = endDatePicker.getMonth();
		endYear = endDatePicker.getYear();
		
		//create two new instances of the calendar
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();
		
		//set the starting date for the first instance of the calendar
		sDate.set(startYear, startMonth, startDate);
		//set the ending date for the second instance of the calendar
		eDate.set(endYear, endMonth, endDate);
		
		//Compare the two dates to see if they are in order or not
		int comparison = eDate.compareTo(sDate);
		
		//if they are equal or are in order, comparison will be equal to 0 or 1
		if (comparison >= 0) {
			//return true because the values of the dates are in order
			check = true;
		}
		
		return check;
	}

}
