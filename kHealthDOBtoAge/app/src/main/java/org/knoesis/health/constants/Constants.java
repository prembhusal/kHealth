package org.knoesis.health.constants;

/**************
 *
 * @author Marupudi Surendra Brahma
 *
 *This class stores the all constants used for the database operations.
 */

public class Constants {
	//Shared Preferences
	public static String MY_PREFS_NAME = "prefAdmin";



	//Constants for Current-Day-Night Symptoms
	public static boolean CURRENT = false;
	public static boolean CURRENT_AND_DAY = false;
	public static boolean CURRENT_AND_NIGHT = false;
	public static boolean NIGHT_DAY = false;




	//observations database constants
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "sensors_db";
	//Observations table name
	public static final String OBSERVATION_TABLE_NAME = "observations";
	//Observations table fields
	public static final String OBSERVA_TABLE_CURRENT_SYMPTOMS = "Current_Symptoms";
	public static final String OBSERVATION_TABLE_USER_NAME = "user";
	public static final String OBSERVATION_TABLE_SENSOR_NAME = "sensor_name";
	public static final String OBSERVATION_TABLE_OBS_VALUE = "value";
	public static final String OBSERVATION_TABLE_TIME_STAMP = "timestamp";

	public static final double  variation_co=.1;
	public static final double  variation_no=.1;
	public static final double  variation_humidity=1;
	public static final double  variation_temperature=1;

	//Constants for the NODE from new framework 1.7
	public static final int MESSAGE_OXA_READING = 9;
	public static final int MESSAGE_OXA_BASELINE_A = 10;
	public static final boolean NODE_AVAILABLE = false;
	public static final boolean SPIROMETRY_AVAILABLE = true;

	public static final String FLOAT_VALUE_KEY = "org.knoesis.health.FLOAT_READING_KEY";


	//Creating active sensors table
	public static final String CREATE_OBSERVATION_TABLE="CREATE TABLE "+OBSERVATION_TABLE_NAME+"("
			+OBSERVATION_TABLE_USER_NAME+" TEXT ,"+OBSERVATION_TABLE_SENSOR_NAME+"  INTEGER  ,"+OBSERVATION_TABLE_OBS_VALUE+
			"  DOUBLE ,"+OBSERVATION_TABLE_TIME_STAMP+" DATETIME )";
	//Active sensors Database
	public static final String ACTIVE_SENSORS_TABLE_NAME = "active_sensors";
	public static final String ACTIVE_SENSORS_TABLE_SENSOR_NAME = "sensor_name";
	public static final String ACTIVE_SENSORS_TABLE_VALUE = "explanation_set";

    //Users table
	public static final String USERS_TABLE_NAME="usersdata";
	public static final String USERS_TABLE_USER_NAME="name";
	//public static final String USERS_TABLE_DOB="dob";
	public static final String USERS_TABLE_AGE="age";

	public static final String USERS_TABLE_GENDER="gender";
	public static final String USERS_TABLE_ZIPCODE = "zipCode";
	public static final String USERS_TABLE_OTHER_ZIPCODE = "otherZipCode";
	public static final String USERS_TABLE_ALBUTEROL = "albuterol";
	public static final String USERS_TABLE_VENTOLIN = "ventolin";
	public static final String USERS_TABLE_PROAIR = "proair";
	public static final String USERS_TABLE_XOPONEX = "xoponex";
	public static final String USERS_TABLE_ATROVENT = "atrovent";
	public static final String USERS_TABLE_COMBIVENT = "combivent";
	public static final String USERS_TABLE_DUONEB = "duoneb";
	public static final String USERS_TABLE_DULERA = "dulera";
	public static final String USERS_TABLE_SYMBICORT = "symbicort";
	public static final String USERS_TABLE_ADVAIR = "advair";
	public static final String USERS_TABLE_FLOVENT = "flovent";
	public static final String USERS_TABLE_ASMANEX = "asmanex";
	public static final String USERS_TABLE_QVAR = "qvar";
	public static final String USERS_TABLE_PULMICORT = "pulmicort";
	public static final String USERS_TABLE_BUDESONIDE = "budesonide";
	public static final String USERS_TABLE_PREDNISONE = "prednisone";
	public static final String USERS_TABLE_PREDNISOLONE = "prednisolone";
	public static final String USERS_TABLE_ORAPRED = "orapred";
	public static final String USERS_TABLE_MONTEKULAST = "montekulast";
	public static final String USERS_TABLE_SINGULAIR = "singulair";
	public static final String CREATE_USERS_TABLE="CREATE TABLE "+ USERS_TABLE_NAME+"("+
			USERS_TABLE_USER_NAME+"  TEXT ,"+USERS_TABLE_AGE +" INTEGER ,"+USERS_TABLE_GENDER + " TEXT ,"+USERS_TABLE_ZIPCODE +" INTEGER,"+USERS_TABLE_OTHER_ZIPCODE +" INTEGER,"+USERS_TABLE_ALBUTEROL +" INTEGER," +
			""+USERS_TABLE_VENTOLIN +" INTEGER,"+USERS_TABLE_PROAIR +" INTEGER, "+USERS_TABLE_XOPONEX +" INTEGER,"+USERS_TABLE_ATROVENT +" INTEGER, "+USERS_TABLE_COMBIVENT +" INTEGER," +
			""+USERS_TABLE_DUONEB +" INTEGER, "+USERS_TABLE_DULERA +" INTEGER,"+USERS_TABLE_SYMBICORT +" INTEGER ,"+USERS_TABLE_ADVAIR +" INTEGER,"+USERS_TABLE_FLOVENT +" INTEGER," +
			""+USERS_TABLE_ASMANEX +" INTEGER,"+USERS_TABLE_QVAR +" INTEGER, "+USERS_TABLE_PULMICORT +" INTEGER, "+USERS_TABLE_BUDESONIDE +" INTEGER,"+USERS_TABLE_PREDNISONE +" INTEGER," +
			""+USERS_TABLE_PREDNISOLONE +" INTEGER, "+USERS_TABLE_ORAPRED +" INTEGER, "+USERS_TABLE_MONTEKULAST +" INTEGER, "+USERS_TABLE_SINGULAIR +" INTEGER )";


	//Questionnaire for Asthma patients
	public static final String questionnaire_cough="";
	public static final String questionnaire_wake="";
	public static final String questionnaire_wheeze="";
	public static final String questionnaire_activity="";
	public static final String questionnaire_rescueInhaler="";
	public static final String questionnaire_comment="";

	//Questions and Answers table
	public static final String QUESTIONS_ANSWERS_TABLE_NAME="questions_and_answers";
	//Fields list for questions and answers table
	public static final String QUESTIONS_ANSWERS_USER_NAME="user";
	public static final String QUESTIONS_ANSWERS_ID="id";
	public static final String QUESTIONS_ANSWERS_ANSWER="answer";
	public static final String QUESTIONS_ANSWERS_TIMESTAMP="timestamp";
	//Creating current symptoms table
	public static final String CREATE_CURRENT_SYMPTOMS_TABLE="create table "+OBSERVA_TABLE_CURRENT_SYMPTOMS+
			"("+QUESTIONS_ANSWERS_USER_NAME+" TEXT ,"
			+QUESTIONS_ANSWERS_ID+" INTEGER ,"+
			QUESTIONS_ANSWERS_ANSWER+" TEXT ,"+
			QUESTIONS_ANSWERS_TIMESTAMP+" DATETIME )";
	//creating the questions and answers table
	public static final String CREATE_QUESTIONS_ANSWERS_TABLE="create table "+QUESTIONS_ANSWERS_TABLE_NAME+
			"("+QUESTIONS_ANSWERS_USER_NAME+" TEXT ,"
			+QUESTIONS_ANSWERS_ID+" INTEGER ,"+
			QUESTIONS_ANSWERS_ANSWER+" TEXT ,"+
			QUESTIONS_ANSWERS_TIMESTAMP+" DATETIME )";

	//Creating active sensors table
	public static final String CREATE_ACTIVE_SENSORS_TABLE="CREATE TABLE "+ACTIVE_SENSORS_TABLE_NAME+"("
			+ ACTIVE_SENSORS_TABLE_SENSOR_NAME + "  TEXT ," + ACTIVE_SENSORS_TABLE_VALUE + " INTEGER )";

    //Previously connected sensors addresses and names table.
	public static final String PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME="previously_connected";
	//Fields list for previously connected sensors addresses and names table.
	public static final String PREVIOUSLY_CONNECTED_SENSORS_ADDRESS="address";
	public static final String PREVIOUSLY_CONNECTED_SENSORS_NAME="name";
	//Creating previously connected sensors addresses and names table.
	public static final String CREATE_PREVIOUSLY_CONNECTED_SENSORS_TABLE="CREATE TABLE "+ PREVIOUSLY_CONNECTED_SENSORS_TABLE_NAME+"("
			+PREVIOUSLY_CONNECTED_SENSORS_ADDRESS+" TEXT , "+ PREVIOUSLY_CONNECTED_SENSORS_NAME+" TEXT)";

	// Creating the admin password table
	public static final String ADMIN_TABLE = "admins";
	// Admin table columns
	public static final String ADMIN_USERNAME = "username";
	public static final String ADMIN_PASS = "pass";
	public static final String CREATE_ADMIN_TABLE = "CREATE TABLE " + ADMIN_TABLE + " ( " +
			ADMIN_USERNAME + " TEXT, " + ADMIN_PASS + " TEXT)";

	//Create the foobot table
	public static final String FOOBOT_TABLE_NAME = "foobot";
	//foobot table columns
	public static final String FOOBOT_TIMESTAMP = "timestamp";
	public static final String FOOBOT_PARTICULATE_MATTER = "particulate_matter";
	public static final String FOOBOT_TEMPERATURE = "temperature";
	public static final String FOOBOT_HUMIDITY = "humidity";
	public static final String FOOBOT_CARBON_DIOXIDE = "carbon_dioxide";
	public static final String FOOBOT_VOLATILE_ORGANIC_COMPOUNDS = "volatile_organic_compounds";
	public static final String FOOBOT_POLLUTION = "pollution";
	public static final String CREATE_FOOBOT_TABLE = "CREATE TABLE " + FOOBOT_TABLE_NAME + " (" +
			FOOBOT_TIMESTAMP + " TEXT, " + FOOBOT_PARTICULATE_MATTER + " REAL, " + FOOBOT_TEMPERATURE +
			" REAL, " + FOOBOT_HUMIDITY + " REAL, " + FOOBOT_CARBON_DIOXIDE + " REAL, " +
			FOOBOT_VOLATILE_ORGANIC_COMPOUNDS + " REAL, " + FOOBOT_POLLUTION + " REAL)";

	// Create a table to store the medication answers
	public static final String MEDICATION_ANSWERS_TABLE_NAME = "medication_answers";
	public static final String MEDICATION_ANSWERS_USER = "user";
	public static final String MEDICATION_ANSWERS_TYPE = "type";
	public static final String MEDICATION_ANSWERS_TIMESTAMP = "timestamp";
	// Medication answers table columns will be loaded from the medication arrays
	public static String getCreateMedicationAnswersTable(){
		StringBuilder creator = new StringBuilder();
		creator.append("CREATE TABLE ").append(MEDICATION_ANSWERS_TABLE_NAME).append(" (");
		creator.append(MEDICATION_ANSWERS_USER).append(" TEXT, ");
		for(String medicine : shortTermMedications){
			creator.append(medicine).append(" INTEGER, ");
		}
		for(String medicine : longTermMedications){
			creator.append(medicine).append(" INTEGER, ");
		}
		creator.append(MEDICATION_ANSWERS_TYPE).append(" TEXT, ");
		creator.append(MEDICATION_ANSWERS_TIMESTAMP).append(" DATETIME)");
		return creator.toString();
	}

	// Create an error log table
	public static final String LOG_TABLE_NAME = "log";
	public static final String LOG_TYPE = "type";
	public static final String LOG_MESSAGE = "error_message";
	public static final String LOG_TIMESTAMP = "timestamp";
	public static final String CREATE_LOG_TABLE = "CREATE TABLE " + LOG_TABLE_NAME + " (" +
			LOG_TYPE + " TEXT, " + LOG_MESSAGE + " TEXT, " + LOG_TIMESTAMP + " TEXT)";

	//error messages
	public static final String ERROR = "error";
	public static final String NO_BLUETOOTH_SUPPORT = "This device does not support Bluetooth";
	public static final String BLUETOOTH_NOT_ENABLED = "Bluetooth Not Enabled";

	//Questionnaire answers
	//Current Question spinner
	public static final String alb_in_30[] = { "No","Yes" };

	// These are the options for the coughing level drop down menu
	public static final String wake_with_cough[] = { "No","Yes" };

	// These are the options for the wake from sleep drop down menu
	public static final String inhaler_at_night[] = { "No", "Yes" };

	// These are the options for the wheezing drop down menu
	public  static final String wheeze[] = { "None", "Less than half the day", "Half the day",
			"Most of the day" };

	// These are the options for the activity level drop down menu
	public static final String activity[] = { "None", "A little","At least half of day",
			"Most of the day" };

	// These are the options for the rescue inhaler amount drop down menu
	public static final String rescueInhaler[] = { "0", "1", "2", "3", "4", "5", "6+" };

	//User gender for creating a user
	public static final String gender[] = {"Male", "Female"};


	public static final String limitActivtyValues[] = {"None", "A little", "At least half of day",
			"Most of the day"};

	//These are the options for Pollen level
	public static final String PollenLevel[]={"LOW","LOW-MEDIUM","MEDIUM","MED-HIGH","HIGH"};

	// List of observed short term medications
	public static final String shortTermMedications[] ={"albuterol", "ventolin", "proair", "xoponex",
			"atrovent", "combivent", "duoneb"};

	public static final int getIndexFromShortTermMedicationsList(String medication){
		int index = -1;
		for(int i = 0; (i < shortTermMedications.length) && (index < 0); i++){
			if (shortTermMedications[i].equals(medication)){
				index = i;
			}
		}
		return index;
	}

	// List of observed long term medications
	public static final String longTermMedications[] = {"dulera", "symbicort", "advair", "flovent",
			"asmanex", "qvar", "pulmicort", "budesonide", "prednisone", "prednisolone", "orapred",
			"montekulast", "singulair"};

	public static final int getIndexFromLongTermMedicationsList(String medication){
		int index = -1;
		for(int i = 0; (i < longTermMedications.length) && (index < 0); i++){
			if (longTermMedications[i].equals(medication)){
				index = i;
			}
		}
		return index;
	}

	//Sensor names as strings
	public static String nodeSensor="node";
	public static String sensordroneSensor="sensordrone";
	//Sensor names
	public static enum sensors{
		// 0
		no,
		// 1
		co,
		// 2
		humidity,
		// 3
		temperature,
		// 4
		pollenLevel,
		// 5
		AQI,
		// 6
		outdoorTemperature,
		// 7
		outdoorHumidity,
		// 8
		pressure,
		// 9
		illuminance,
		// 10
		capacitance,
		// 11
		externalVoltage,
		// 12
		altitude,
		// 13
		spirometry
	}

	//Different graphs
	public static enum graphs{
		no,
		co,
		humidity,
		temperature,
		cough,
		sleep,
		wheezing,
		activity,
		inhalerusage,
		pollenLevel,
		AQI,
		outdoorTemperature,
		outdoorHumidity
	}

	public enum questionnaireIndicies{
        UNUSED,
        COUGH_LEVEL,
        WAKE_FROM_SLEEP,
        TIGHTNESS,
        ACTIVITY,
        TAKE_ALBUTEROL,
        COMMENT,
        COUGH,
        WHEEZE,
        CHEST,
        BREATH,
        NOSE,
        SENTENCES,
        OTHER
    }
	/*This is used to store the exact value to differentiate the sensors and questionnaire graphs, 
	 * here it is 3 may be change in future based on the 
	*/
	public static final int differentiator=3;

	/*This is used to store the comments id in questionnaire and it is used to retrieve the comments.
	*/
	public static final String commentsId="6";

	//Sensor observations 


	//Initial sensor values message
	public static final String InitialSensorMessage="Reading...";

	//Intial sensor value
	public static final double InitialSensorValue=0;

	//Sql Lite database address
	public static final String AsthmaDataBaseAddress="/data/data/org.knoesis.health/databases/sensors_db";

	//Database External file name for user retrieval purpose
	public static final String ExternalDatabaseFileName="khealthAsthmaObservations.sql";


	//Graph activity error messages
	public static final String error_no_records="No past observations";

	// API keys and URLs
	// wunderground.com
	public static final String WUND_KEY = "e1e6815f15919bf5";
	public static final String WUND_URL = "http://api.wunderground.com/api/" + WUND_KEY + "/";

	// breezometer.com
	public static final String BREE_KEY = "da803f986da54fa3906868085dbd1161";
	public static final String BREE_URL_START = "http://api-beta.breezometer.com/baqi/?location=";
	public static final String BREE_URL_END = "&fields=breezometer_aqi&key=" + BREE_KEY;

}//end of Constants