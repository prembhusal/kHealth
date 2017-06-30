package org.knoesis.health;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TableRow.LayoutParams;

import com.sensorcon.sensordrone.Drone;
import com.sensorcon.sensordrone.Drone.DroneEventListener;
import com.sensorcon.sensordrone.Drone.DroneStatusListener;
import com.variable.framework.android.bluetooth.BluetoothService;
import com.variable.framework.node.NodeDevice;

import org.knoesis.health.constants.Constants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * We will make a class that extends Application to put our Drone in, that way
 * we can use it in multiple activities.
 */
public class AsthmaApplication extends Application {

    protected Drone myDrone;
    //This stores the answers for Current Questions
    public int answer_current_cough = 0;
    public int answer_current_wheeze = 0;
    public int answer_current_chest = 0;
    public int answer_current_breath_hard_fast = 0;
    public int answer_current_nose_opens_wide = 0;
    public int answer_cant_talk_full_sentences = 0;

    public int getAnswer_current_breath_hard_fast() {
        return answer_current_breath_hard_fast;
    }

    public void setAnswer_current_breath_hard_fast(
            int answer_current_breath_hard_fast) {
        this.answer_current_breath_hard_fast = answer_current_breath_hard_fast;
    }

    public int getAnswer_current_nose_opens_wide() {
        return answer_current_nose_opens_wide;
    }

    public void setAnswer_current_nose_opens_wide(int answer_current_nose_opens_wide) {
        this.answer_current_nose_opens_wide = answer_current_nose_opens_wide;
    }

    public int getAnswer_cant_talk_full_sentences() {
        return answer_cant_talk_full_sentences;
    }

    public void setAnswer_cant_talk_full_sentences(
            int answer_cant_talk_full_sentences) {
        this.answer_cant_talk_full_sentences = answer_cant_talk_full_sentences;
    }

    public String answer_current_other = "";
    public int answer_albuterol_in_30 = 0;

    public int getAnswer_albuterol_in_30() {
        return answer_albuterol_in_30;
    }

    public void setAnswer_albuterol_in_30(int answer_albuterol_in_30) {
        this.answer_albuterol_in_30 = answer_albuterol_in_30;
    }

    public int getAnswer_current_cough() {
        return answer_current_cough;
    }

    public void setAnswer_current_cough(int answer_current_cough) {
        this.answer_current_cough = answer_current_cough;
    }

    public int getAnswer_current_wheeze() {
        return answer_current_wheeze;
    }

    public void setAnswer_current_wheeze(int answer_current_wheeze) {
        this.answer_current_wheeze = answer_current_wheeze;
    }

    public int getAnswer_current_chest() {
        return answer_current_chest;
    }

    public void setAnswer_current_chest(int answer_current_chest) {
        this.answer_current_chest = answer_current_chest;
    }


    public String getAnswer_current_other() {
        return answer_current_other;
    }

    public void setAnswer_current_other(String answer_current_other) {
        this.answer_current_other = answer_current_other;
    }

    // This stores the which user is active in the current session.
    public String userLoggedIn = "";
    public int answer_yesterday = 0;
    public int answer_cough = 0;
    public int answer_wake = 0;
    public int answer_wheeze = 0;
    public int answer_activity = 0;
    public int answer_rescue = 0;
    public int answer_wheeze_amount = 0;
    public String answer_comment = "";
    public boolean sensorDroneConnection = false;
    public boolean individualReading = false;
    private static Context context;

    // These two global variables are used for the alarm feature
    public boolean vibrateCheck = false;
    public boolean soundCheck = false;

    // This variable will hold a dynamic list of classes to call in order
    ArrayList<Class> activityChain = new ArrayList<>();
    int activityChainIndex = 0;

    /*******
     * These are the methods for setting the values for the boolean values of
     * the alarm feature
     *******/
    // set the vibrate on or off
    public void setVibrateCheck(boolean value) {
        this.vibrateCheck = value;
    }

    // get the value for the vibrate, whether it is on or off
    public boolean getVibrateCheck() {
        return vibrateCheck;
    }

    // set the sound on or off
    public void setSoundCheck(boolean value) {
        this.soundCheck = value;
    }

    // get the value for the sound, whether it is on or off
    public boolean getSoundCheck() {
        return soundCheck;
    }

    /**
     * These are all of the setters and getters for the questions from the
     * questionnaire
     */
    // These two methods set and get whether the user answers for today or
    // previous day
    public int getAnswer_yesterday() {
        return answer_yesterday;
    }

    public void setAnswer_yesterday(int answer_yesterday) {
        this.answer_yesterday = answer_yesterday;
    }

    // These two methods set and get whether or not the user woke from a cough
    // or wheeze at night
    public void setAnswerToCoughLastNight(int answer) {
        this.answer_cough = answer;
    }

    public int getAnswerToCoughLastNight() {
        return answer_cough;
    }

    // These two methods set and get whether or not the rescue inhaler was used
    // at night
    public void setAnswerToWakeLastNight(int wake) {
        this.answer_wake = wake;
    }

    public int getAnswerToWakeLastNight() {
        return answer_wake;
    }

    // These two methods set and get how many times the rescue inhaler was used
    // during the day
    public void setNumberOfTimesInhalerUsed(int rescue_amount) {
        this.answer_rescue = rescue_amount;
    }

    public int getNumberOfTimesInahlerUsed() {
        return answer_rescue;
    }

    // These two methods set and get
    public void setWheezeChoice(int value) {
        this.answer_wheeze = value;
    }

    public int getWheezeChoice() {
        return answer_wheeze;
    }

    // These two methods set and get
    public void setWheezeAmount(int value) {
        this.answer_wheeze_amount = value;
    }

    public int getWheezeAmount() {
        return answer_wheeze_amount;
    }

    public boolean getIndividualReading() {
        return individualReading;
    }

    public void setIndividualReading(boolean individualReading) {
        this.individualReading = individualReading;
    }

    // Set some streaming rates, so we can switch back to a default rate when
    // coming back from graphing.
    protected int defaultRate = 1000;
    protected int streamingRate;
    // This is used to store the current date
    Date date = new Date();
    // this is used to store the timestamp
    protected Timestamp current_timestamp = new Timestamp(date.getTime());
    // this is used to store the previous timestamp
    int dayInMs = 1000 * 60 * 60 * 24;
    protected Timestamp previous_timestamp = new Timestamp(date.getTime() - dayInMs);
    // This is used to store the last timestamp when the NO value updated to the
    // db
    protected Timestamp NO_timestamp = new Timestamp(date.getTime());
    // This is used to store the last timestamp when the CO values updated to
    // the db
    protected Timestamp co_timestamp = new Timestamp(date.getTime());
    // This is used to store the last timestamp when the CO values updated to
    // the db
    protected Timestamp humidity_timestamp = new Timestamp(date.getTime());
    // This is used to store the last timestamp when the CO values updated to
    // the db
    protected Timestamp temperature_timestamp = new Timestamp(date.getTime());

    /****************
     * NODE NO Variables and methods
     **********************************************************************/

    private static BluetoothService mBluetoothService;
    public final ArrayList<BluetoothDevice> mDiscoveredDevices = new ArrayList<BluetoothDevice>();
    public static NodeDevice mActiveNode = null;
    // This is used for identify whether the node device is connected using user
    // selected sensor or using previously connected node sensor address.
    public Boolean usedPreviousNodedeviceAddress = false;

    public static final BluetoothService getService() {
        return mBluetoothService;
    }

    public static final BluetoothService setServiceAPI(BluetoothService api) {
        mBluetoothService = api;
        return mBluetoothService;
    }

    public void setActiveNode(NodeDevice node) {
        mActiveNode = node;
    }

    public static NodeDevice getActiveNode() {
        return mActiveNode;
    }

    public double previousNOReading = Constants.InitialSensorValue;
    /*****************
     * End of NODE NO variables and methods
     *******************************************************************/

    // to store the previous sensor values for CO, Humidity , Temperature
    protected double previous_co_reading = Constants.InitialSensorValue;
    protected double previous_humidity_reading = Constants.InitialSensorValue;
    protected double previous_temperature_reading = Constants.InitialSensorValue;
    // This is used for identify whether the node device is connected using user
    // selected sensor or using previously connected node sensor address.
    public Boolean usedPreviousSensordronedeviceAddress = false;

    /*
     * Preferences
     */
    protected SharedPreferences sdcPreferences;


    // Our Listeners
    protected DroneEventListener deListener;
    protected DroneStatusListener dsListener;

    // An int[] that will hold the QS_TYPEs for our sensors of interest
    protected int[] qsSensors;

    // check to see if the user is trying to access the sequential or stand
    // alone sensor observations
    private boolean isSequential;

    // Text to display
    public static final String[] SENSOR_NAMES = {"Temperature (Ambient)",
            "Humidity",
            "Pressure",
            "Object Temperature (IR)",
            "Illuminance (Lux calculated from RGB sensor)",
            "Precision Gas (CO equivalent)",
            "Proximity Capacitance",
            "External Voltage (0-3V)",
            "Altitude (calculated)"
    };
    public static final String SENSOR_BATTERY_VOLTAGE = "Battery Voltage";

    // Figure out how many sensors we have based on the length of our labels
    protected int numberOfSensors = SENSOR_NAMES.length;

    // We only want to notify of a low battery once,
    // but the event might be triggered multiple times.
    // We use this to try and show it only once
    protected boolean lowbatNotify;

    /*
     * Our TableRow layout
     */
    protected LayoutParams trLayout = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    /*
     * Our TextView label layout
     */
    protected LayoutParams tvLayout = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    /*
     * Our ToggleButton layout
     */
    protected LayoutParams tbLayout = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, .1f);

    // this will store the sensor values table created or not
    protected Boolean sensorValuesTableCreated = false;

    // this will store the questionnaire table created or not
    protected Boolean questionnaireTableCreated = false;

    // This will hold the whether the sensor listeners are created or not
    public Boolean listenersActivated = false;

    public Activity currentActivity;

	/*
     * These two methods make a call to either set the current user or to get
	 * the name of the current user
	 */

    public void setCurrentLoggedInUser(String user) {
        this.userLoggedIn = user;
    }

    public String getCurrentLoggedInUser() {
        return userLoggedIn;
    }

    // set whether the user is accessing the activity to be sequential or not
    public void setSequentialCheck(boolean value) {
        isSequential = value;
    }

    // get the value of the boolean isSequential to see if the buttons should
    // appear in the activity or not
    public boolean getSequentialCheck() {
        return isSequential;
    }

    // Just added this for trial - vaikunth
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Just added this for trial - vaikunth
        context = this;
        /*
        Below statement needs min target of 21
         */
//        com.variable.application.NodeApplication.initialize(this);
        myDrone = new Drone();
        streamingRate = defaultRate;
        // Initialize SharedPreferences
        sdcPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


        qsSensors = new int[]{myDrone.QS_TYPE_TEMPERATURE,
                myDrone.QS_TYPE_HUMIDITY, myDrone.QS_TYPE_PRESSURE,
                myDrone.QS_TYPE_IR_TEMPERATURE, myDrone.QS_TYPE_RGBC,
                myDrone.QS_TYPE_PRECISION_GAS, myDrone.QS_TYPE_CAPACITANCE,
                myDrone.QS_TYPE_ADC, myDrone.QS_TYPE_ALTITUDE};


    }

    /**
     * Things to do when we disconnect
     */
    public void doOnDisconnect() {
        // Shut off any sensors that are on

        new Thread(new Runnable() {

            @Override
            public void run() {

                // Turn off myBlinker
                // myBlinker.disable();

                // Make sure the LEDs go off
                if (myDrone.isConnected) {
                    // Turn off myBlinker
                    // myBlinker.disable();
                    myDrone.setLEDs(0, 0, 0);
                }

                // Only try and disconnect if already connected
                if (myDrone.isConnected) {
                    myDrone.disconnect();
                }

            }
        });
        com.variable.application.NodeApplication.unbindServiceAndReceiver();

    }

    // Stuff to do when we're trying to reconnect on connection lost
    public void connectionLostReconnect() {
        // Re-Toggle and sensors that were on
        new Thread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }


}