package org.knoesis.health;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.knoesis.health.constants.Constants;
import org.knoesis.health.dataHolders.UserDataHolder;
import org.xml.sax.InputSource;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class PopulationSignals extends KHealthAsthmaParentActivity {
    private ProgressDialog pd;
    private final int NETWORK_RETRIES = 1;
    private TextView txtAQI;
    private TextView txtPollenLevel;
    private TextView txtTemperature;
    private TextView txtHumidity;

    private String AQI = "";
    private String AQIMessage = "";
    private String PollenLevel = "";
    private String PollenIndex = "";
    private String Temperature = "";
    private String Humidity = "";


    private List<UserDataHolder> data;
    String zipcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracking);
        String user = asthmaApp.getCurrentLoggedInUser();
        SharedPreferences prefs = getSharedPreferences(asthmaApp.userLoggedIn + "Prefs", 0);
        zipcode = prefs.getInt("zipCode", 0) + "";
        Log.d("Zipcode", zipcode);

        // Get a reference to UI elements
        txtAQI = (TextView) findViewById(R.id.airQualityIndex);
        txtPollenLevel = (TextView) findViewById(R.id.pollenCount);
        txtTemperature = (TextView) findViewById(R.id.txt_temperature);
        txtHumidity = (TextView) findViewById(R.id.humidity);
        Button finish = (Button) findViewById(R.id.finish);

        // Create and begin background task
        GetPopulationSignals getPopulationSignals = new GetPopulationSignals(this);
        getPopulationSignals.execute();

        finish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.w("From PopulationSignals", "Invoking AlarmService to trigger Email notifications");
                Calendar calendar = Calendar.getInstance();

                // we can set time by open date and time picker dialog

                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 45);
                calendar.set(Calendar.SECOND, 0);

                Intent intent1 = new Intent(PopulationSignals.this, EmailSendingReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        PopulationSignals.this, 0, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) PopulationSignals.this
                        .getSystemService(PopulationSignals.ALARM_SERVICE);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                Intent iR = new Intent(PopulationSignals.this, RewardsActivity.class);
                startActivity(iR);
                finish();

            }
        });

        // Button to go back to the NODE inputs
        Button previousNODE = (Button) findViewById(R.id.previousNODE);

        boolean isSequential = ((AsthmaApplication) getApplication()).getSequentialCheck();

        //check the value, if true, don't show the button
        if (!isSequential) {
            //finish.setVisibility(View.INVISIBLE);
            finish.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            previousNODE.setVisibility(View.INVISIBLE);
        }

        previousNODE.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /***********
     * This method will update the database with
     */
    public void updateDatabase() {
        // Adding the rows to database related to all these if the status is
        // true
        // Converting the pollen level to respective index value to store in
        // database.

        /* The following uses an Enum class in the Costants.java file. This ensures that value only
        * needs to be changed in one place. To access the integer value of the sensor use the
        * following:
        *       Constants.sensors.[sensor name].ordinal();
        * This will return an integer equal to the sensor's position in the Enum class.
        */
        try {
            // Make sure pollen level has valid content
            if (!PollenIndex.equals("-")) {
                // Get pollen sensor number from constants and use it to add pollen to the database
                addRowtoSensorVlauesTable(Constants.sensors.pollenLevel.ordinal(),
                        Double.valueOf(PollenIndex));
            }
            // Make sure the AQI has valid content
            if (!AQI.equals("-")) {
                // Get AQI "sensor" number from constants and use it to add AQI to the database
                addRowtoSensorVlauesTable(Constants.sensors.AQI.ordinal(),
                        Double.valueOf(AQI));
            }
            if (!Temperature.equals("-")) {
                // Get temperature sensor number from constants and use it to add temp to the database
                addRowtoSensorVlauesTable(
                        Constants.sensors.outdoorTemperature.ordinal(),
                        Double.valueOf(Temperature));
            }
            if (!Humidity.equals("-")) {
                // Get humidity sensor number from constants and use it to add humidity to the database
                addRowtoSensorVlauesTable(
                        Constants.sensors.outdoorHumidity.ordinal(),
                        Double.valueOf(Humidity.substring(0, Humidity.length() - 1)));
            }
        } catch (NumberFormatException exception) {
            // TODO: Handle number format exception
            Log.e("DB", "Error inserting values in DB");
        }

    }

    /**
     * Queries for the air quality index and return it as a string.
     * @param zip The zip code to be queried
     * @return The AQI in string format if successful, an empty string otherwise.
     */
    private String getAQI(String zip) {
        String aqi = "", message = "";
        InputSource inputSource;
        String expression, url;
        XPath xpath = XPathFactory.newInstance().newXPath();


        // Expression to extract the AQI from XML file
        expression = "//AirQualityIndex/AQI/text()";
        // make URL to get the AQI using ZIP code
        url = "http://sonicbanana.cs.wright.edu/asthma/PopulationSignals/AirQualityIndex.php?zip="
                + zip;
        // get input from the specified URL.

        // Attempt to query for AQI
        inputSource = new InputSource(url);
        try {
            // get AQI from XML file
            aqi = xpath.evaluate(expression, inputSource);
            // Expression to extract the AQI message from XML file
            expression = "//AirQualityIndex/Message/text()";
            // get AQI Message from XML file
            message = xpath.evaluate(expression, inputSource);
        } catch (XPathExpressionException xExpression) { // If the query cannot be completed
            // Add a row to the Log table and return an empty string.
            Log.e("PopulationSignals", "Could not retrieve AQI.");
            addRowToLogTable("ERROR", "Could not retrieve AQI. PopulationSignals.java.",
                    new Timestamp(date.getTime()).toString());
            return "";
        }

        return aqi.trim() + " (" + message + ")";
    }

    /**
     * Queries for the pollen index and level and return them as a string.
     * @param zip The zip code to query
     * @return The pollen index and level in string format if successful, an empty string otherwise.
     */
    private String getPollenLevel(String zip) {
        String index, level, url, expression;
        InputSource inputSource;
        XPath xpath = XPathFactory.newInstance().newXPath();

        // Expression to extract the Pollen Level from XML file
        expression = "//Pollen/Level/text()";
        // make URL to get the Pollen Level using ZIP code
        url = "http://sonicbanana.cs.wright.edu/asthma/PopulationSignals/Pollen2.php?zip="
                + zip;
        // get input from the specified URL.
        inputSource = new InputSource(url);
        try {
            // get Pollen Level from XML file
            level = xpath.evaluate(expression, inputSource);
            // Expression to extract the Pollen Level from XML file
            expression = "//Pollen/Index/text()";
            // get Pollen Level from XML file
            index = xpath.evaluate(expression, inputSource);
        } catch (XPathExpressionException xException) {
            addRowToLogTable("ERROR", "Could not retrieve pollen level. PopulationSignals.java.",
                    new Timestamp(date.getTime()).toString());
            return "";
        }

        return index.trim() + " (" + level + ")";
    }

    /**
     * Queries for the temperature and humidity levels and returns the combination as a string.
     * @param zip The zip code to query.
     * @return The temperature and humidity in string format if successful, "-:-" otherwise
     */
    private String getTemperatureAndHumidity(String zip) {
        String temp, humidity, url;
        temp = humidity = "";

        url = Constants.WUND_URL + "conditions/q/" + zip + ".json";
        Log.d("PopulationSignals", "Preparing to parse json");
        JsonParser wundParser = new JsonParser(url);
        Log.d("PopulationSignals", "parsed json");
        JSONObject wund = wundParser.getJsonObject();
        try {
            if (wund != null) {
                System.out.println(wund.length());
                JSONObject wund_cond = wund.getJSONObject("current_observation");

                Log.d("TEMP", wund_cond.names().join(","));

                temp = wund_cond.get("temp_f").toString();
                humidity = wund_cond.get("relative_humidity").toString();
            } else {
                throw new JSONException("Empty response");
            }
        } catch (JSONException jException) {
            addRowToLogTable("ERROR", "Could not retrieve temperature and humidity. PopulationSignals.java.",
                    new Timestamp(date.getTime()).toString());
            return "-:-";
        } catch (NullPointerException npe) {
            addRowToLogTable("ERROR", "Returned JSONObject is null. PopulationSignals.java",
                    new Timestamp(date.getTime()).toString());
            return "-:-";
        }

        return temp + ":" + humidity;
    }

    /**
     * A background task that will query the population signals.
     */
    private class GetPopulationSignals extends AsyncTask<Void, Void, Boolean> {
        private PopulationSignals populationSignals;

        private GetPopulationSignals(PopulationSignals ps) {
            this.populationSignals = ps;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(PopulationSignals.this);
            pd.setTitle("Retrieving...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            // Get ZIP code from XML file using the expression.
            String zip = zipcode;
            String tempAndHum = "-:-";
            // Repeat until success or desired tries reached.
            for (int i = 0; i < NETWORK_RETRIES; i++) {
                // If AQI is empty, query for it.
                if (AQIMessage.equals("")) {
                    AQIMessage = getAQI(zip);
                }
                // If pollen level is empty, query for it.
                if (PollenLevel.equals("")) {
                    PollenLevel = getPollenLevel(zip);
                }
                // If temperature or humidity is empty, query for them.
                if (tempAndHum.equals("-:-") || tempAndHum.equals(":")) {
                    tempAndHum = getTemperatureAndHumidity(zip);
                    Temperature = tempAndHum.split(":")[0];
                    Humidity = tempAndHum.split(":")[1];
                }
            }
            // Check if each signal was successfully retrieved. If not, replace it with a "-".
            if (AQIMessage.equals("")) {
                AQIMessage = "-";
                AQI = "-";
            } else {
                AQI = AQIMessage.split(" ")[0];
            }
            if (PollenLevel.equals("")) {
                PollenLevel = "-";
            } else {
                PollenIndex = PollenLevel.split(" ")[0];
            }
            if (Temperature.equals("") || Temperature.equals("-")) {
                Temperature = "-";
            }
            if (Humidity.equals("") || Humidity.equals("-")) {
                Humidity = "-";
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            // If the progress bar showing then dismiss it here.

            if (pd.isShowing()) {
                pd.dismiss();
            }

            if (!b) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                PopulationSignals.this);
                        alert.setTitle("Couldn't get zipcode");
                        alert.setMessage("Unable to locate the zipcode of current locaiton");
                        alert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        // If you wanted to try again
                                        // directly, you could add that
                                        // here (and add a
                                        // "cancel button" to not scan
                                        // again).
                                    }
                                });

                        alert.show();
                    }
                });
            } else {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Adding the values to textviews
                        txtAQI.setText(AQIMessage);
                        txtPollenLevel.setText(PollenLevel);
                        txtTemperature.setText(Temperature + " " + (char) 0x00B0 + "F");
                        txtHumidity.setText(Humidity);
                        populationSignals.updateDatabase();

                    }
                });

            }

        }
    }

}