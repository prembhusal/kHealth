package org.knoesis.health;

//import org.knoesis.health.adapters.MenuListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ibm.icu.util.Calendar;

import org.knoesis.health.adapters.MenuListAdapter;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.DataHolder;
import org.knoesis.health.dataHolders.PreviouslyConnectedSensorsDataHolder;

import org.knoesis.health.dataHolders.UserDataHolder;
import org.knoesis.health.database.DatabaseHandler;
import org.knoesis.health.database.SensorValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class KHealthAsthmaParentActivity extends Activity {

	// protected MenuListAdapter mlAdapter;
	protected PopupWindow menu;
	protected View layout;
	protected MenuListAdapter mlAdapter;

	protected int height;

	// Database helper for observations database
	DatabaseHandler db = new DatabaseHandler(this, Constants.DATABASE_NAME,
			Constants.DATABASE_VERSION);

	// This is used to store the current date
	Date date = new Date();

	/*
	 * We put all our global variables in a class that extends Application so it
	 * can be accessed in multiple activities.
	 */
	AsthmaApplication asthmaApp;

	/*
	 * Preferences
	 */
	protected SharedPreferences sdcPreferences;

	protected Activity activity = this;

	// Button for Home
	Button btnHome;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.menu_list, null);

		// Initializing the current activity

		// Get out Application so we have access to our Drone
		asthmaApp = (AsthmaApplication) getApplication();

		// Initialize SharedPreferences
		sdcPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// this is used for to hold the options in Drop Down Menu.
		ArrayList<DataHolder> data = new ArrayList<DataHolder>();

		data.add(0, new DataHolder(R.drawable.home, R.string.home));

		// Connection Options Activity for drop down menu
		data.add(1, new DataHolder(R.drawable.blue_heart,R.string.userInputs));

		// Settings Activity for drop down menu
		//data.add(3, new DataHolder(R.drawable.blue_chart,R.string.past_observations));
		//Summarization
		data.add(2, new DataHolder(R.drawable.final_summary,R.string.summary));
		//Rewards
		data.add(3, new DataHolder(R.drawable.rewards,R.string.rewards));
		// Help Activity for drop down menu
		data.add(4, new DataHolder(R.drawable.blue_help,R.string.connection_options_help));

		// Settings Activity for drop down menu
		data.add(5, new DataHolder(R.drawable.settings, R.string.Settings));

		//Observation Activity for drop down menu
		// TODO: Uncomment the following line to re-enable Individual Sensor Readings
		//data.add(6, new DataHolder(R.drawable.blue_ind_readings,R.string.individualReadings));

		// Change user activity for drop down menu
		//data.add(5, new DataHolder(R.drawable.blue_change_user, R.string.changeUser));

		// Button to exit the application
		data.add(6, new DataHolder(R.drawable.exitapp, R.string.exitapp));
		
		mlAdapter = new MenuListAdapter(data, this);
		ListView lv = ((ListView) layout.findViewById(R.id.menuListView));
		lv.setAdapter(mlAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {

				if (position == 0) {
					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							HomeActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}// end of if

				if (position == 1) {
					Intent i;
					i = new Intent(KHealthAsthmaParentActivity.this,DayNightCurrentMenu.class);
					startActivity(i);
				}// end of if
				if(position == 2){
					Intent i = new Intent(KHealthAsthmaParentActivity.this, GraphSelectionActivity.class);
					startActivity(i);
				}// end of if

				if(position == 3){
					Intent i = new Intent(KHealthAsthmaParentActivity.this, RewardsActivity.class);
					startActivity(i);
				}// end of if
				/*// Connection Options Activity
				if (position == 1) {
					menu.dismiss();

					((AsthmaApplication) getApplication())
							.setSequentialCheck(true);
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							Observations_Sensordrone.class);

					startActivity(i);
				}// end of if
				*/

				// TODO: Uncomment the following lines to re-enable Individual Sensor Readings
				/*if (position == 6) {
					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							ObservationsActivity.class);

					startActivity(i);

				}// end of if*/
                /*
				// Position 3 Help Activity
				if (position == 3) {
					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							GraphSelectionActivity.class);
					startActivity(i);

				}// end of if*/

				// Position 3 is for Settings activity
				if (position == 5) {
					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							SettingsActivity.class);

					startActivity(i);

				}// end of if

				/*if (position == 5) {
					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							ChangeUserActivity.class);
					startActivity(i);

				}*/// end of if
					// Position 4 Help Activity
				if (position == 4) {

					menu.dismiss();
					Intent i = new Intent(KHealthAsthmaParentActivity.this,
							HelpActivity.class);
					startActivity(i);

				}// end of if

				if (position == 6) {
					menu.dismiss();
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							KHealthAsthmaParentActivity.this);

					// set title
					alertDialogBuilder.setTitle("");

					// set dialog message
					alertDialogBuilder
							.setMessage("Are you sure?")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog, int id) {
											asthmaApp.userLoggedIn = "";
											moveTaskToBack(true);
											finish();
											System.exit(0);
										}
									})

							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, just
											// close
											// the dialog box and do nothing
											dialog.cancel();
										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();
				}

			}
		});

		lv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		height = layout.getMeasuredHeight();
		height = lv.getMeasuredHeight();
		height *= (data.size() - .5);
		menu = new PopupWindow(layout, 400, height, true);
		menu.setBackgroundDrawable(new BitmapDrawable());
		menu.setOutsideTouchable(true);

		// Remove the Title bar at the top
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// smoothes gradients
		Window window = this.getWindow();
		window.setFormat(PixelFormat.RGBA_8888);

	}

	/****
	 * 
	 * This method will invoke when ever user clicks on drop down menu button in
	 * top right corner.
	 * 
	 * @param v
	 */

	public void onClickMenu(View v) {
		// assigning the height. If the required height is more than the current
		// window size then set the current window height.
		if (height > menu.getMaxAvailableHeight(v))
			menu.setHeight(menu.getMaxAvailableHeight(v));
		else
			// otherwise set actual weight.
			menu.setHeight(height);
		menu.setWidth(500);
		menu.showAsDropDown(v, 0, -18);
	}

	@Override
	public void onStop() {
		menu.dismiss();
		super.onStop();
	}

	/*************
	 * 
	 * When user clicks on the home button this method will invoke startup
	 * activity to display home screen of the application
	 * 
	 **************/
	public void onClickHome(View v) {

		// Intent i = new Intent(this, HomeActivity.class);
		// finishing the current activity
		finish();
		// startActivity(i);
	}

	/*************
	 * 
	 * When user clicks on the Back button this method will finish the current
	 * activity to goto previous activity.
	 * 
	 **************/
	/*public void onClickBack(View v) {

		finish();

	}*/

	/*
	 * A function to display Toast Messages.
	 * 
	 * By having it run on the UI thread, we will be sure that the message is
	 * displays no matter what thread tries to use it.
	 */
	public void quickMessage(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	/**
	 * 
	 * @param datePicker
	 * @return a java.util.Date
	 */
	/*public static Date getDateFromDatePicket(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime();
	}*/

	/***********************************************************************************************
	 * This will add the row to the previously connected sensors addresses and
	 * names table.
	 * 
	 * @param // sensorName
	 * @param //sensorAddress
	 ***********************************************************************************************/
	/*public void addRowtoPreviouslyConnectedSensorsTable(final String name,
			final String address) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				*//*****
				 * 
				 * If the previously connected sensors table is not already
				 * created then create table.
				 * 
				 *******//*

				if (!db.isTableExists(Constants.PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME)) {
					db.createTable(Constants.CREATE_PREVIOUSLY_CONNECTED_SENSORS_TABLE);

				} else {
					*//**
					 * If users table already exist then add the user to users
					 * table.
					 **//*
					db.addRowtoPreviouslyConnectedSensorsTable(new PreviouslyConnectedSensorsDataHolder(
							name, address));
					db.writeToFile();
				}
			}
		});

	}*/

	/***********************************************************************************************
	 * This will add the row to the users table
	 * 
	 * @param //sensorName
	 * @param //sensorValue
	 ***********************************************************************************************/
	/*public void addRowtoUsersTable(final String name, final String dob, final String gender , final int zipCode,final int otherZipCode,final int albuterol, final int ventolin,final int proair,final int xoponex,final int atrovent,
								   final int combivent, final int duoneb, final int dulera,final int symbicort,final int advair,final int flovent,final int asmanex,
								   final int qvar,final int pulmicort,final int budesonide,final int prednisone,final int prednisolone,
								   final int orapred,final int montekulast,final int singulair ) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				*//*****
				 * 
				 * If the users table is not already created then create that
				 * users table.
				 * 
				 *******//*

				if (!db.isTableExists(Constants.USERS_TABLE_NAME)) {
					db.createTable(Constants.CREATE_USERS_TABLE);

				} else {
					*//**
					 * If users table already exist then add the user to users
					 * table.
					 **//*
					db.addRowtoUsersTable(new UserDataHolder(name, dob, gender, zipCode,otherZipCode,albuterol, ventolin, proair, xoponex, atrovent,
							combivent, duoneb,  dulera, symbicort, advair,flovent, asmanex,
							qvar, pulmicort, budesonide, prednisone,prednisolone,
							orapred, montekulast,singulair ));
					db.writeToFile();
				}
			}
		});

	}*/

	/**
	 * This will add the row to the sensor values table
	 * 
	 * @param sensorName
	 * @param sensorValue
	 */
	public void addRowtoSensorVlauesTable(final int sensorName,
			final Double sensorValue) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				/**
				 * Checking if the sensor values table already exists or not.
				 */
				if (asthmaApp.sensorValuesTableCreated == true) {

					// If both are not equal then add the row to the sensor
					// values table
					db.addRowToSensorValues(new SensorValues(
							asthmaApp.current_timestamp, sensorName,
							sensorValue), asthmaApp.userLoggedIn);
					db.writeToFile();

				} else {
					/**
					 * 
					 * If the default observations table is not already created
					 * then create that table.
					 */
					if (!db.isTableExists(Constants.OBSERVATION_TABLE_NAME)) {
						db.createTable(Constants.CREATE_OBSERVATION_TABLE);
					}
					asthmaApp.sensorValuesTableCreated = true;
					// create the sensor values table and call this method
					// again.
					addRowtoSensorVlauesTable(sensorName, sensorValue);
				}
			}
		});

	}

	/**
	 * This will add the row to the Questionnaire table
	 * 
	 * @param //sensorName
	 * @param //sensorValue
	 */
	public void addRowtoQuestionnaireTable(final int id, final String answer) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				/**
				 * Checking if the Questionnaire table already exists or not.
				 */
				if (asthmaApp.questionnaireTableCreated == true) {
					Date date = new Date();
					asthmaApp.current_timestamp = new Timestamp(date.getTime());
					// If both are not equal then add the row to the sensor
					// values table
					asthmaApp.previous_timestamp = new Timestamp(date.getTime()-asthmaApp.dayInMs);
					if(asthmaApp.answer_yesterday==1){
						db.addRowToQuestionnaire(id, answer,
								asthmaApp.previous_timestamp.toString(),
								asthmaApp.userLoggedIn);
						db.writeToFile();
						
						}
					
					else{
					db.addRowToQuestionnaire(id, answer,
							asthmaApp.current_timestamp.toString(),
							asthmaApp.userLoggedIn);
					db.writeToFile();
					}

				} else {
					/**
					 * 
					 * If the default questionnaire table is not already created
					 * then create that table.
					 */
					if (!db.isTableExists(Constants.QUESTIONS_ANSWERS_TABLE_NAME)) {
						db.createTable(Constants.CREATE_QUESTIONS_ANSWERS_TABLE);

					}
					asthmaApp.questionnaireTableCreated = true;
					// create the sensor values table and call this method
					// again.
					addRowtoQuestionnaireTable(id, answer);
				}
			}
		});

	}

	public void addRowToMedicationAnswersTable(ArrayList<Integer> answers, String timestamp, String type, String user){
		if(!db.isTableExists(Constants.MEDICATION_ANSWERS_TABLE_NAME)){
			db.createTable(Constants.getCreateMedicationAnswersTable());
			addRowToMedicationAnswersTable(answers, timestamp, type, user);
		}else{
			db.addRowToMedicationAnswersTable(answers, timestamp, type, user);
		}
	}

	public void addRowToLogTable(String type, String message, String timestamp){
		if(!db.isTableExists(Constants.LOG_TABLE_NAME)){
			db.createTable(Constants.CREATE_LOG_TABLE);
			addRowToLogTable(type, message, timestamp);
		}else{
			db.addRowToLogTable(type, message, timestamp);
		}
	}

	/*public void finishActivity() {
		this.finish();
	}*/

	/*protected View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
