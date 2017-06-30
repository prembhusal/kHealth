package org.knoesis.health;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.Toast;

import org.knoesis.health.constants.Constants;
import org.knoesis.health.database.DatabaseSummaryHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class ActivitySummaryAdmin extends KHealthAsthmaAdminActivity {
	// Text views that contain the results
	TextView	wakeNo, wakeYes,
	albuterolNo, albuterolYes,
	coughNone, coughLessHalf, coughHalf, coughMost,
	limitNone, limitLessHalf, limitHalf, limitMost,
	lessThan2, moreThan2;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary_new);
		
		
		//Date Pickers for start/end date
		final DatePicker startDatePicker;
		final DatePicker endDatePicker;
		
		// Create strings to hold the minimum and maximum timestamps
		String minDate = "", maxDate = "";
		
		// Object to access database
		final DatabaseSummaryHelper dbHelper;
		
		// String storing the user name
		final String user;
		
		//Set text views
		wakeNo = (TextView) findViewById(R.id.tvNoCount1);
		wakeYes = (TextView) findViewById(R.id.tvYesCount1);
		albuterolNo = (TextView) findViewById(R.id.tvNoCount2);
		albuterolYes = (TextView) findViewById(R.id.tvYesCount2);
		coughNone = (TextView) findViewById(R.id.tvN1);
		coughLessHalf = (TextView) findViewById(R.id.tvL1);
		coughHalf = (TextView) findViewById(R.id.tvH1);
		coughMost = (TextView) findViewById(R.id.tvM1);
		limitNone = (TextView) findViewById(R.id.tvN2);
		limitLessHalf = (TextView) findViewById(R.id.tvL2);
		limitHalf = (TextView) findViewById(R.id.tvH2);
		limitMost = (TextView) findViewById(R.id.tvM2);
		lessThan2 = (TextView) findViewById(R.id.tvLess2);
		moreThan2 = (TextView) findViewById(R.id.tvGreat2);
		
		// Set the datepickers
		startDatePicker = (DatePicker) findViewById(R.id.date_start);
		endDatePicker = (DatePicker) findViewById(R.id.date_end);
		
		//Set the textview for Number of days 
		final TextView tvNOD = (TextView) findViewById(R.id.textViewNODValue);
		
		
		// Get the database
		dbHelper = new DatabaseSummaryHelper(getApplicationContext());
		try{
			dbHelper.createDataBase();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		user = ((AsthmaApplication) getApplication()).getCurrentLoggedInUser();
		
		// Check to see if the Questions and Answers table exists
		if(!dbHelper.isTableExists(Constants.QUESTIONS_ANSWERS_TABLE_NAME)){
			showAlert();
			Log.d("Summary", "Questions and Answers table does not exist.");
		}else if(dbHelper.getAll(user, 1, 0) == 0
				&& dbHelper.getAll(user, 1, 1) == 0
				&& dbHelper.getAll(user, 2, 0) == 0
				&& dbHelper.getAll(user, 2, 1) == 0
				&& dbHelper.getAll(user, 3, 0) == 0
				&& dbHelper.getAll(user, 3, 1) == 0
				&& dbHelper.getAll(user, 3, 2) == 0
				&& dbHelper.getAll(user, 3, 3) == 0
				&& dbHelper.getAll(user, 4, 0) == 0
				&& dbHelper.getAll(user, 4, 1) == 0
				&& dbHelper.getAll(user, 4, 2) == 0
				&& dbHelper.getAll(user, 4, 3) == 0
				&& dbHelper.getAll2(user, 5, "<", 2) == 0
				&& dbHelper.getAll2(user, 5, ">", 2) == 0){
			for(int i = 1; i <= 5; i++){
				int j_max = 2;
				if(i == 3 || i == 4){
					j_max = 4;
				}
				if(i == 5){
					System.out.println(dbHelper.getAll2(user, i, "<", 2));
					System.out.println(dbHelper.getAll2(user, i, "<", 2));
					continue;
				}
				for(int j = 0; j < j_max; j++){
					System.out.println(dbHelper.getAll(user, i, j));
				}
			}
			Log.d("Summary", "No user data available.");
			showAlert();
		}else{
			
			//Set the Number of Days Answered TextView with the result from the getCountOfObservations
			long countObservations = dbHelper.getCountOfAnsweredQuestionsPerDay(user);
			SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_WORLD_READABLE); 

			tvNOD.setText(Long.toString(countObservations)+" / "+prefs.getInt("numberofdeploymentdays",0));
			
			
			minDate = dbHelper.getMinDate(user);
			maxDate = dbHelper.getMaxDate(user);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try{
				Date min = formatter.parse(minDate);
				final Calendar minCal = Calendar.getInstance();
				minCal.setTime(min);
				minCal.set(Calendar.HOUR_OF_DAY, 0);
				minCal.set(Calendar.MINUTE, 0);
				Date max = formatter.parse(maxDate);
				final Calendar maxCal = Calendar.getInstance();
				maxCal.setTime(max);
				maxCal.set(Calendar.HOUR_OF_DAY, 23);
				maxCal.set(Calendar.MINUTE, 59);
				startDatePicker.setMinDate(minCal.getTimeInMillis());
				startDatePicker.setMaxDate(maxCal.getTimeInMillis());
				endDatePicker.setMinDate(minCal.getTimeInMillis());
				endDatePicker.setMaxDate(maxCal.getTimeInMillis());
				
				startDatePicker.init(minCal.get(Calendar.YEAR), minCal.get(Calendar.MONTH), 
						minCal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener(){

							@Override
							public void onDateChanged(DatePicker view,
									int year, int monthOfYear, int dayOfMonth) {
								minCal.set(Calendar.YEAR, year - 1);
								endDatePicker.setMinDate(minCal.getTimeInMillis());
								minCal.set(Calendar.YEAR, year);
								minCal.set(Calendar.MONTH, monthOfYear);
								minCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								minCal.set(Calendar.HOUR_OF_DAY, 0);
								minCal.set(Calendar.MINUTE, 0);
								endDatePicker.setMinDate(minCal.getTimeInMillis());
								Timestamp time = new Timestamp(minCal.getTimeInMillis());
								requery(time, new Timestamp(maxCal.getTimeInMillis()), dbHelper, user);
							
								
							
							}
							
							
							
					
				}
				
						
						
						
						);
				
				endDatePicker.init(maxCal.get(Calendar.YEAR), maxCal.get(Calendar.MONTH), 
						maxCal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener(){

							@Override
							public void onDateChanged(DatePicker view,
									int year, int monthOfYear, int dayOfMonth) {
								maxCal.set(Calendar.YEAR, year);
								maxCal.set(Calendar.MONTH, monthOfYear);
								maxCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								maxCal.set(Calendar.HOUR_OF_DAY, 23);
								maxCal.set(Calendar.MINUTE, 59);
								startDatePicker.setMaxDate(maxCal.getTimeInMillis());
								Timestamp time = new Timestamp(maxCal.getTimeInMillis());
								requery(new Timestamp(minCal.getTimeInMillis()), time, dbHelper, user);
							
							
								
							
							
							}
					
				});
				
//				startDatePicker.init(year, monthOfYear, dayOfMonth, onDateChangedListener);
			}catch(ParseException e){
				
			}
			
//			Timestamp minTime = new Timestamp(theYear, theMonth, theDate, theHour, theMinute, theSecond, theNano)
			
			// Populate the text views
			// Question 1
			int count = dbHelper.getAll(user, 1, 0);
			wakeNo.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 1, 1);
			wakeYes.setText(Integer.toString(count));
			// Question 2
			count = dbHelper.getAll(user, 2, 0);
			albuterolNo.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 2, 1);
			albuterolYes.setText(Integer.toString(count));
			// Question 3
			count = dbHelper.getAll(user, 3, 0);
			coughNone.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 3, 1);
			coughLessHalf.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 3, 2);
			coughHalf.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 3, 3);
			coughMost.setText(Integer.toString(count));
			// Question 4
			count = dbHelper.getAll(user, 4, 0);
			limitNone.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 4, 1);
			limitLessHalf.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 4, 2);
			limitHalf.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 4, 3);
			limitMost.setText(Integer.toString(count));
			// Question 4
			count = dbHelper.getAll2(user, 5, "<", 2);
			lessThan2.setText(Integer.toString(count));
			count = dbHelper.getAll2(user, 5, ">", 2);
			moreThan2.setText(Integer.toString(count));
			
			
			
			
			
		}
	}

	@Override
	public void onPause(){
		super.onPause();
		((AsthmaApplication) getApplication()).setCurrentLoggedInUser("");
	}
	
	private void showAlert(){
		AlertDialog alert = new AlertDialog.Builder(ActivitySummaryAdmin.this).create();
		alert.setTitle("No Readings");
		alert.setMessage("There are no readings submitted!");
		alert.setIcon(R.drawable.warning_alert);
		alert.setButton(AlertDialog.BUTTON_POSITIVE, "Back", 
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),
								"PleaseComplete the questionnaire", 
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(ActivitySummaryAdmin.this, AdminActivity.class);
						startActivity(i);
					}
			
		});
		alert.show();
	}
	
	private void requery(Timestamp begin, Timestamp end, DatabaseSummaryHelper dbHelper, String user){
		// Question 1
		int count = dbHelper.getAllBetween(user, 1, 0, begin, end);
		wakeNo.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 1, 1, begin, end);
		wakeYes.setText(Integer.toString(count));
		// Question 2
		count = dbHelper.getAllBetween(user, 2, 0, begin, end);
		albuterolNo.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 2, 1, begin, end);
		albuterolYes.setText(Integer.toString(count));
		// Question 3
		count = dbHelper.getAllBetween(user, 3, 0, begin, end);
		coughNone.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 3, 1, begin, end);
		coughLessHalf.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 3, 2, begin, end);
		coughHalf.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 3, 3, begin, end);
		coughMost.setText(Integer.toString(count));
		// Question 4
		count = dbHelper.getAllBetween(user, 4, 0, begin, end);
		limitNone.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 4, 1, begin, end);
		limitLessHalf.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 4, 2, begin, end);
		limitHalf.setText(Integer.toString(count));
		count = dbHelper.getAllBetween(user, 4, 3, begin, end);
		limitMost.setText(Integer.toString(count));
		// Question 4
		count = dbHelper.getAll2Between(user, 5, "<", 2, begin, end);
		lessThan2.setText(Integer.toString(count));
		count = dbHelper.getAll2Between(user, 5, ">", 2, begin, end);
		moreThan2.setText(Integer.toString(count));
	}

}