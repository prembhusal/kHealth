package org.knoesis.health.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import org.knoesis.health.AsthmaApplication;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.CommentsDataHolder;
import org.knoesis.health.dataHolders.FoobotDataHolder;
import org.knoesis.health.dataHolders.PreviouslyConnectedSensorsDataHolder;
import org.knoesis.health.dataHolders.SensorDataHolder;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**********************************
 * 
 * @author Marupudi Surendra Brahma
 * 
 * THis class is used to handle all the database operations like
 * Inserting the data into specific table.
 * Updating the data in specific table.
 * Retrieving the records from the database of specific table.
 * Writing the database to external file for user convenience. 
 *
 */

public class DatabaseHandler extends SQLiteOpenHelper {



	//This is default constructor by only passing the context connect to the database
	public DatabaseHandler(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}
	
	/********
	 * THis constructor is useful to provide connection to the custom database.
	 * @param context   this provides the context 
	 * @param DATABASE_NAME this parameter provides the database name
	 * @param DATABASE_VERSION this parameter provides the database version.
	 */
	public DatabaseHandler(Context context,String DATABASE_NAME, int  DATABASE_VERSION) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Constants.CREATE_ADMIN_TABLE);
		ContentValues adminValues = new ContentValues();
		adminValues.put(Constants.ADMIN_USERNAME, "kHealthAdmin"); // username
		adminValues.put(Constants.ADMIN_PASS, "0bd90d6b5edfa7e69c618db8e668bf87b52972d4"); // sha1 of password
		db.insert(Constants.ADMIN_TABLE, null, adminValues);
		// If more admin users need to be added, repeat the above 3 lines
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}
	/****
	 * This method creates the database table.
	 * 
	 * @param query  this provides the query to create the new table
	 */
	public void createTable(String query)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.execSQL(query);
	}
	
	
	
/**************************      Inserting data into the database tables ************************************************/	
	
	
	
	/**
	 * 
	 * This method will add the default which  sensors  are active  to collect observations from the sensordrone.
	 * 
	 */
	public void addDefaultRowsToActiveSensorsTable()
	{	//creating default rows for active sensors table
		// Ambient Temperature
		activeSensors row=new activeSensors(AsthmaApplication.SENSOR_NAMES[0],1);
		addRowToActiveSensorsTable(row);
		// Humidity
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[1],1);
		addRowToActiveSensorsTable(row);
		// Pressure
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[2],1);
		addRowToActiveSensorsTable(row);
		// IR temperature
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[3],1);
		addRowToActiveSensorsTable(row);
		// Illuminance
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[4],1);
		addRowToActiveSensorsTable(row);
		// Precision gas (CO)
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[5],1);
		addRowToActiveSensorsTable(row);
		// Proximity capacitance
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[6],1);
		addRowToActiveSensorsTable(row);
		// External voltage
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[7],1);
		addRowToActiveSensorsTable(row);
		// Altitude
		row=new activeSensors(AsthmaApplication.SENSOR_NAMES[8],1);
		addRowToActiveSensorsTable(row);
		row=new activeSensors(AsthmaApplication.SENSOR_BATTERY_VOLTAGE,0);
		addRowToActiveSensorsTable(row);
		
	}
	
	
	
	/***
	 * This method will add rows to active sensors table.
	 * 
	 * @param row
	 */
	public void addRowToActiveSensorsTable(activeSensors row)
	{
		//creating the variable to store the all fields values of the activesensors table.
		ContentValues values = new ContentValues();
		//adding fields to the values
		values.put(Constants.ACTIVE_SENSORS_TABLE_SENSOR_NAME,row.getSensorName());
		values.put(Constants.ACTIVE_SENSORS_TABLE_VALUE, row.getValue());
				//Invoking the method to add row to active sensors table.
		addRecord(Constants.ACTIVE_SENSORS_TABLE_NAME,values);
		
	}
	
	/*******************
	 * This will add the observations to observations table 
	 * @param sensorValues
	 */
	
	public void addRowToSensorValues(SensorValues sensorValues,String user)
	{	
		//creating the variable to store the all fields values of the sensorValues table.
		ContentValues values = new ContentValues();
		//adding the fields to the values
		values.put(Constants.OBSERVATION_TABLE_USER_NAME,user);
		values.put(Constants.OBSERVATION_TABLE_SENSOR_NAME, sensorValues.getSensorName());
		values.put(Constants.OBSERVATION_TABLE_OBS_VALUE,sensorValues.getSensorValue());
		values.put(Constants.OBSERVATION_TABLE_TIME_STAMP,sensorValues.getTimestamp().toString());
		//adding the record to the observations table
		addRecord(Constants.OBSERVATION_TABLE_NAME,values);
		
	}
	
	/****************************
	 * This will add the questions answers to the Questions and answers table
	 * 
	 * @param id
	 * @param answer
	 * @param current_timestamp
	 */
	public void addRowToQuestionnaire(int id,String answer,String current_timestamp,String user)
	{
		//creating the variable to store the all fields values of the questionnaire table.
		ContentValues values = new ContentValues();
	
		//adding the fields to the values
		values.put(Constants.QUESTIONS_ANSWERS_USER_NAME,user);
		values.put(Constants.QUESTIONS_ANSWERS_ID, id);
		values.put(Constants.QUESTIONS_ANSWERS_ANSWER, answer);
		values.put(Constants.QUESTIONS_ANSWERS_TIMESTAMP, current_timestamp);
		
		//Adding the record to the questionnaire table
		addRecord(Constants.QUESTIONS_ANSWERS_TABLE_NAME,values);
	}


	public void deleteUserRecord(String username)
	{
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		try
		{
			// Delete Query
			String deleteQuery = "DELETE FROM "+Constants.USERS_TABLE_NAME+" WHERE "+Constants.USERS_TABLE_USER_NAME+"='"+username+"'";
			db.execSQL(deleteQuery);
		}
		finally
		{
			//Closing the  database connection after completing the previously connected sensors retrieval from the database.
			db.close();
		}
	}
	/****************************
	 * This will add the questions answers to the Current_Symptoms table
	 * 
	 * @param id
	 * @param answer
	 * @param current_timestamp
	 */	
	public void addRowToQuestionnaire2(int id,String answer,String current_timestamp,String user)
	{
		//creating the variable to store the all fields values of the questionnaire table.
		ContentValues values = new ContentValues();
	
		//adding the fields to the values
		values.put(Constants.QUESTIONS_ANSWERS_USER_NAME,user);
		values.put(Constants.QUESTIONS_ANSWERS_ID, id);
		values.put(Constants.QUESTIONS_ANSWERS_ANSWER, answer);
		values.put(Constants.QUESTIONS_ANSWERS_TIMESTAMP, current_timestamp);
		
		//Adding the record to the questionnaire table
		addRecord(Constants.OBSERVA_TABLE_CURRENT_SYMPTOMS,values);
	}
	/*******
	 * This will add row to PreviouslyConnectedSensors for current connected sensor.
	 * 
	 * @param
	 * 
	 *******/
	public void addRowtoPreviouslyConnectedSensorsTable(PreviouslyConnectedSensorsDataHolder sensorDataholder)
	{
		//creating the variable to store the all fields values of the sensorValues table.
		ContentValues values = new ContentValues();
		//adding the fields to the values
		values.put(Constants.PREVIOUSLY_CONNECTED_SENSORS_NAME, sensorDataholder.getName());
		values.put(Constants.PREVIOUSLY_CONNECTED_SENSORS_ADDRESS,sensorDataholder.getAddress());
		//adding the record to the users table
		addRecord(Constants.PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME,values);
	}
	/*******
	 * This will add the newly created user to the users table.
	 * 
	 * @param userDataHolder
	 * 
	 *******/
	public void addRowtoUsersTable(UserDataHolder userDataHolder)
	{
		//creating the variable to store the all fields values of the sensorValues table.
		ContentValues values = new ContentValues();
		//adding the fields to the values
		values.put(Constants.USERS_TABLE_USER_NAME, userDataHolder.getName());
		//values.put(Constants.USERS_TABLE_DOB,userDataHolder.getDOB());
		values.put(Constants.USERS_TABLE_AGE,userDataHolder.getAge());
		values.put(Constants.USERS_TABLE_GENDER, userDataHolder.getGender());
		values.put(Constants.USERS_TABLE_ZIPCODE, userDataHolder.getZipCode());
		values.put(Constants.USERS_TABLE_ALBUTEROL , userDataHolder.getAlbuterol());
		values.put(Constants.USERS_TABLE_VENTOLIN , userDataHolder.getVentolin());
		values.put(Constants.USERS_TABLE_PROAIR , userDataHolder.getProair());
		values.put(Constants.USERS_TABLE_XOPONEX , userDataHolder.getXoponex());
		values.put(Constants.USERS_TABLE_ATROVENT , userDataHolder.getAtrovent());
		values.put(Constants.USERS_TABLE_COMBIVENT , userDataHolder.getCombivent());
		values.put(Constants.USERS_TABLE_DUONEB , userDataHolder.getDuoneb());
		values.put(Constants.USERS_TABLE_DULERA , userDataHolder.getDulera());
		values.put(Constants.USERS_TABLE_SYMBICORT , userDataHolder.getSymbicort());
		values.put(Constants.USERS_TABLE_ADVAIR , userDataHolder.getAdvair());
		values.put(Constants.USERS_TABLE_FLOVENT , userDataHolder.getFlovent());
		values.put(Constants.USERS_TABLE_ASMANEX , userDataHolder.getAsmanex());
		values.put(Constants.USERS_TABLE_QVAR , userDataHolder.getQvar());
		values.put(Constants.USERS_TABLE_PULMICORT , userDataHolder.getPulmicort());
		values.put(Constants.USERS_TABLE_BUDESONIDE , userDataHolder.getBudenoside());
		values.put(Constants.USERS_TABLE_PREDNISONE , userDataHolder.getPrednisone());
		values.put(Constants.USERS_TABLE_PREDNISOLONE , userDataHolder.getPrednisolone());
		values.put(Constants.USERS_TABLE_ORAPRED , userDataHolder.getOrapred());
		values.put(Constants.USERS_TABLE_MONTEKULAST , userDataHolder.getMontelukast());
		values.put(Constants.USERS_TABLE_SINGULAIR , userDataHolder.getSingulair());


			//adding the record to the users table
		addRecord(Constants.USERS_TABLE_NAME, values);
	}

	public void addRowToFoobotTable(FoobotDataHolder foobotDataHolder){
		if(!isTableExists(Constants.FOOBOT_TABLE_NAME)){
			createTable(Constants.CREATE_FOOBOT_TABLE);
			addRowToFoobotTable(foobotDataHolder);
		}
		ContentValues values = new ContentValues();

		values.put(Constants.FOOBOT_TIMESTAMP, foobotDataHolder.getTimestamp());
		values.put(Constants.FOOBOT_PARTICULATE_MATTER, foobotDataHolder.getParticulateMatter());
		values.put(Constants.FOOBOT_TEMPERATURE, foobotDataHolder.getTemperature());
		values.put(Constants.FOOBOT_HUMIDITY, foobotDataHolder.getHumidity());
		values.put(Constants.FOOBOT_CARBON_DIOXIDE, foobotDataHolder.getCarbonDioxide());
		values.put(Constants.FOOBOT_VOLATILE_ORGANIC_COMPOUNDS, foobotDataHolder.getVolatileOrganicCompounds());
		values.put(Constants.FOOBOT_POLLUTION, foobotDataHolder.getPollution());

		addRecord(Constants.FOOBOT_TABLE_NAME, values);
	}

	public void addRowToMedicationAnswersTable(ArrayList<Integer> answers, String timestamp, String type, String user){
		ContentValues values = new ContentValues();
		values.put(Constants.MEDICATION_ANSWERS_USER, user);
		for(int i = 0; i < Constants.shortTermMedications.length; i++){
			values.put(Constants.shortTermMedications[i], answers.get(i));
		}
		for(int i = 0; i < Constants.longTermMedications.length; i++){
			values.put(Constants.longTermMedications[i], answers.get(Constants.shortTermMedications.length + i));
		}
		values.put(Constants.MEDICATION_ANSWERS_TYPE, type);
		values.put(Constants.MEDICATION_ANSWERS_TIMESTAMP, timestamp);

		addRecord(Constants.MEDICATION_ANSWERS_TABLE_NAME, values);
	}

	public void addRowToLogTable(String type, String message, String timestamp){
		ContentValues values = new ContentValues();
		values.put(Constants.LOG_TYPE, type);
		values.put(Constants.LOG_MESSAGE, message);
		values.put(Constants.LOG_TIMESTAMP, timestamp);

		addRecord(Constants.LOG_TABLE_NAME, values);
	}

    public int getZipcode(String username){
        //Retrieving the readable database and executing the query.
        SQLiteDatabase db = this.getReadableDatabase();

        //Instantiating the UserDataHolder list to hold the list of users.
        int zipcode=0;
        try
        {


            // Select All Query
            String selectQuery = "SELECT  "+Constants.USERS_TABLE_ZIPCODE+"  FROM " + Constants.USERS_TABLE_NAME +" WHERE "+Constants.USERS_TABLE_USER_NAME+"='"+username +"'";

            //executing the query.
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //Creating the single observation for each user of the users table.
                    zipcode=cursor.getInt(0);
                    //Adding the user to the users list

                } while (cursor.moveToNext());
            }
            //Closing the  cursor after completing the users retrieval from the database.
            cursor.close();
        }
        catch (Exception e) {
            return 0;
        }
        finally
        {
            //Closing the  database connection after completing the users retrieval from the database.
            db.close();
        }
        //Returning the users list
        return zipcode;
    }
	
	/**
	 * This will add the row to the specific table
	 * @param TABLE_NAME
	 * @param values
	 */
	public void addRecord(String TABLE_NAME, ContentValues values)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Inserting Row
	    long result = db.insert(TABLE_NAME, null, values);
		if(result != -1){
			Log.v("DBHandler", "Successfully added row " + result + " to table " + TABLE_NAME + ".");
		}else{
			Log.e("DBHandler", "Error adding row to table " + TABLE_NAME + ".");
		}
	    db.close(); // Closing database connection
	}
	
	/**
	 * updating the active sensor table records
	 * 
	 * @param sensorName
	 * @param value
	 */
	public void updateActiveSensorsTable(String sensorName,int value)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	  //adding fields to the values.
	    values.put(Constants.ACTIVE_SENSORS_TABLE_SENSOR_NAME,sensorName);
	    values.put(Constants.ACTIVE_SENSORS_TABLE_VALUE, value);
	    
	    db.update(Constants.ACTIVE_SENSORS_TABLE_NAME,values, Constants.ACTIVE_SENSORS_TABLE_SENSOR_NAME+"=?",new String[]{sensorName});
	}
	
	
	/***************************************** Retrieving the data from the tables *************************************************************/
	
	
	/**
	 * This will return 0 or 1 to show for that sensor is active or not
	 * 
	 * @param sensorName
	 */
	public int getActiveSensorValue(String sensorName)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		 Cursor cursor = db.query(Constants.ACTIVE_SENSORS_TABLE_NAME, new String[] { Constants.ACTIVE_SENSORS_TABLE_SENSOR_NAME,
				 Constants.ACTIVE_SENSORS_TABLE_VALUE }, Constants.ACTIVE_SENSORS_TABLE_SENSOR_NAME + "=?",
	                new String[] { sensorName }, null, null, null, null);
	        if (cursor != null)
	            cursor.moveToFirst();
	 
	        
	        // return contact
	        return Integer.parseInt(cursor.getString(1));
	}
	public List<UserDataHolder> getAllUsers()
	{
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		
		//Instantiating the UserDataHolder list to hold the list of users.
		List<UserDataHolder> users=new ArrayList<UserDataHolder>();
		try
		{
					
					
				// Select All Query
				String selectQuery = "SELECT  "+Constants.USERS_TABLE_USER_NAME+
							"  FROM " + Constants.USERS_TABLE_NAME;
					
				//executing the query.
				Cursor cursor = db.rawQuery(selectQuery, null);
			
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {			
						//Creating the single observation for each user of the users table.
						UserDataHolder user = new UserDataHolder(cursor.getString(0));
						//Adding the user to the users list
						users.add(user);
					} while (cursor.moveToNext());
				}
				//Closing the  cursor after completing the users retrieval from the database.
				cursor.close();
			}
			catch (Exception e) {
				return null;
			}
			finally
			{
				//Closing the  database connection after completing the users retrieval from the database.
				db.close();
			}
		//Returning the users list
		return users;
	}
	/***************************************
	 * @param sensorName
	 * @return all previously connected sensor names
	 * 
	 * This will return the list of  previous sensors connected of specific type.
	 * 
	 ***************************************/
	public List<PreviouslyConnectedSensorsDataHolder> getAllPreviouslyConnectedSensors(String sensorName) {
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		
		//Instantiating the PreviouslyConnectedSensorsDataHolder list to hold the list of sensors information.
		List<PreviouslyConnectedSensorsDataHolder> sensorsList = new ArrayList<PreviouslyConnectedSensorsDataHolder>();
		try
		{
			
			
			// Select All Query
			String selectQuery = "SELECT  "+Constants.PREVIOUSLY_CONNECTED_SENSORS_ADDRESS+
					"  FROM " + Constants.PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME+
					"  where "+Constants.PREVIOUSLY_CONNECTED_SENSORS_NAME+"='"+sensorName+"'";
			
			
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					
					//Creating the single record for each previously connected sensor for specific sensor
					PreviouslyConnectedSensorsDataHolder observation = new PreviouslyConnectedSensorsDataHolder(cursor.getString(0));
					//Adding the previously connected sensor to the sensors list
					sensorsList.add(observation);
				} while (cursor.moveToNext());
			}
			//Closing the  cursor after completing the previously connected sensors retrieval from the database.
			cursor.close();
		}
		catch (Exception e) {
			return null;
		}
		finally
		{
			//Closing the  database connection after completing the previously connected sensors retrieval from the database.
			db.close();
		}
		

		// return addresses list of specific sensor.
		return sensorsList;
	}
	public void removeAllPreviouslyConnectedSensors(String sensorName)
	{
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		try
		{
			// Delete Query
			String deleteQuery = "DELETE FROM "+Constants.PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME+" WHERE "+Constants.PREVIOUSLY_CONNECTED_SENSORS_NAME+"='"+sensorName+"'";
			db.execSQL(deleteQuery);
		}
		finally
		{
			//Closing the  database connection after completing the previously connected sensors retrieval from the database.
			db.close();
		}
	}
	
	/***************************************
	 * @param sensorName
	 * @return all the observations of specific sensor 
	 * 
	 * This will return the list of  observations of specific sensor using the sensorName parameter
	 * 
	 ***************************************/
	public List<SensorDataHolder> getAllSensorObservations(String sensorName,String user) {
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		
		//Instantiating the SensorDataHolder list to hold the list of observations
		List<SensorDataHolder> observationsList = new ArrayList<SensorDataHolder>();
		try
		{
			
			
			// Select All Query
			String selectQuery = "SELECT  "+Constants.OBSERVATION_TABLE_OBS_VALUE+
					","+Constants.OBSERVATION_TABLE_TIME_STAMP+
					"  FROM " + Constants.OBSERVATION_TABLE_NAME+
					"  where "+Constants.OBSERVATION_TABLE_USER_NAME+"='"+user+"'  AND  "+Constants.OBSERVATION_TABLE_SENSOR_NAME+"='"+sensorName+"' order by "+Constants.OBSERVATION_TABLE_TIME_STAMP+" DESC limit 100";
			
			
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					
					//Creating the single observation for each record of the observations table for specific sensor
					SensorDataHolder observation = new SensorDataHolder(cursor.getDouble(0),cursor.getString(1));
					//Adding the observation to the observations list
					observationsList.add(observation);
				} while (cursor.moveToNext());
			}
			//Closing the  cursor after completing the observations retrieval from the database.
			cursor.close();
		}
		catch (Exception e) {
			return null;
		}
		finally
		{
			//Closing the  database connection after completing the observations retrieval from the database.
			db.close();
		}
		

		// return observations list of specific sensor.
		return observationsList;
	}
	/***************************************
	 * @return all the Comments 
	 * 
	 * This will return the list of  comments.
	 * 
	 */
	public List<CommentsDataHolder> getComments(String commentId, String user) {
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		
		//Instantiating the CommentsDataHolder list to hold the list of comments
		List<CommentsDataHolder> commentsList = new ArrayList<CommentsDataHolder>();
		try
		{
			
			
			// Select All Query
			String selectQuery = "SELECT  "+Constants.QUESTIONS_ANSWERS_ANSWER+
					","+Constants.QUESTIONS_ANSWERS_TIMESTAMP+
					"  FROM " + Constants.QUESTIONS_ANSWERS_TABLE_NAME+
					"  where "+Constants.QUESTIONS_ANSWERS_USER_NAME+"='"+user+"'  AND  "+Constants.QUESTIONS_ANSWERS_ID+"='"+commentId+"' order by "+Constants.QUESTIONS_ANSWERS_TIMESTAMP+" DESC limit 100";
			
			
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					
					//Creating the single comment for specific timestamp
					CommentsDataHolder observation = new CommentsDataHolder(cursor.getString(0),cursor.getString(1));
					//Adding the comment to the comments list
					commentsList.add(observation);
				} while (cursor.moveToNext());
			}
			//Closing the  cursor after completing the comments retrieval from the database.
			cursor.close();
		}
		catch (Exception e) {
			return null;
		}
		finally
		{
			//Closing the  database connection after completing the comments retrieval from the database.
			db.close();
		}
		

		// return comments list.
		return commentsList;
	}
	/***************************************
	 * @param questionId
	 * @return all the answers of specific question 
	 * 
	 * This will return the list of  answers of specific question using the questionId parameter
	 * 
	 */
	public List<SensorDataHolder> getAllAnswers(int questionId,String user) {
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();
		
		//Instantiating the SensorDataHolder list to hold the list of answers
		List<SensorDataHolder> answersList = new ArrayList<SensorDataHolder>();
		try
		{
			
			
			// Select All Query
			String selectQuery = "SELECT  "+Constants.QUESTIONS_ANSWERS_ANSWER+
					","+Constants.QUESTIONS_ANSWERS_TIMESTAMP+
					"  FROM " + Constants.QUESTIONS_ANSWERS_TABLE_NAME+
					"  where "+Constants.QUESTIONS_ANSWERS_USER_NAME+"='"+user+"'  AND  "
					+Constants.QUESTIONS_ANSWERS_ID+"='"+questionId+"' order by "+Constants.QUESTIONS_ANSWERS_TIMESTAMP+" DESC limit 100";
			
			
			Cursor cursor = db.rawQuery(selectQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					//Creating the single observation for each record of the observations table for specific sensor
					SensorDataHolder answer = new SensorDataHolder(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
					//Adding the observation to the observations list
					answersList.add(answer);
				} while (cursor.moveToNext());
			}
			//Closing the  cursor after completing the observations retrieval from the database.
			cursor.close();
		}
		catch (Exception e) {
			return null;
		}
		finally
		{
			//Closing the  database connection after completing the observations retrieval from the database.
			db.close();
		}
		

		// return observations list of specific sensor.
		return answersList;
	}

	public String getUserPassword(String username){
		//Retrieving the readable database and executing the query.
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT " + Constants.ADMIN_PASS + " FROM " + Constants.ADMIN_TABLE +
				" WHERE " + Constants.ADMIN_USERNAME + "='" + username + "'", null);
		String pass = "";
		if(cursor.moveToFirst()) {
			pass = cursor.getString(0);
		}
		return pass;
	}
	
	/****
	 * This method verifies if the specified table already exists or not in specific database. 
	 * 
	 * @param tableName : specific table name
	 * @return  :If table exists it will return true otherwise it will return the false.
	 */
	public boolean isTableExists( String tableName)
	{
		SQLiteDatabase db = this.getReadableDatabase();
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}
	public void writeToFile() {
		// Write to a file
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

		// File path = Environment.
		File file = new File(path, Constants.ExternalDatabaseFileName);

		// Make sure the DCIM directory exists.
		path.mkdirs();

		// Very simple code to copy a sql lite from the data/data/package/
		// resource into the external file. Note that this code does
		// no error checking Note that if external storage is
		// not currently mounted this will silently fail.
		// InputStream is =
		// getResources().openRawResource("/data/data/com.example.myandroidapp/databases/sensors_db");
		try {
			FileInputStream is = new FileInputStream(Constants.AsthmaDataBaseAddress);
			OutputStream os = new FileOutputStream(file);
			byte[] data;

			data = new byte[is.available()];
			is.read(data);
			os.write(data);
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}


