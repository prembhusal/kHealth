package org.knoesis.health;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.variable.framework.dispatcher.DefaultNotifier;
import com.variable.framework.node.NodeDevice;
import com.variable.framework.node.OxaSensor;
import com.variable.framework.node.enums.NodeEnums;
import com.variable.framework.node.reading.SensorReading;

import org.knoesis.health.constants.Constants;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

//import org.knoesis.health.NODE.MessageConstants;

@SuppressLint("HandlerLeak")
public class Observation_NODE_NEW extends KHealthAsthmaParentActivity implements OxaSensor.OxaListener {
	private static final String TAG = Observation_NODE_NEW.class.getName();
	
	private TextView noText;
	private OxaSensor oxa;

	// region Lifecycle Events
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observation__node__no);
		noText = (TextView) findViewById(R.id.txtNO);
		
		
		
		DefaultNotifier.instance().addOxaListener(this);
		
        NodeDevice node = asthmaApp.getActiveNode();
        
        if(node != null)        	
        {
        	/*
        	 * This part of the code will check the battery level and if it is below a certain threshold
        	 * then it will send an alert to the user letting them know to charge the node. 
        	 */
        	
        	double batteryLevel = (double)node.getBatteryLevel();
			//The highest value for the battery in volts is 4.2, in order to work, it must be above 3.5
			batteryLevel   = ((batteryLevel - 3.5) / .7) * 100;
			if(batteryLevel <= 20.0){
			 createBatteryAlert(this);
			}
        	
            oxa = node.findSensor(NodeEnums.ModuleType.OXA);
            oxa.startSensor();
        }
		
		
		//Button to get to the environmental inputs
		Button environmentalInput = (Button) findViewById(R.id.environmentalInput);
		
		//get the value to check if the activity is sequential 
		boolean isSequential = ((AsthmaApplication) getApplication()).getSequentialCheck();
		/*//Remove the following line when population signals should be next.
		environmentalInput.setText("Done");*/
		environmentalInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((AsthmaApplication) getApplication()).setSequentialCheck(true);
				//TODO: Uncomment the following line to include the population signals into the app.
				Intent i = Constants.SPIROMETRY_AVAILABLE ?
                        new Intent(Observation_NODE_NEW.this, SpirometryCollection.class) :
                        new Intent(Observation_NODE_NEW.this, PopulationSignals.class);
				//TODO: Delete the following line when population signals should be next.
				//Intent i = new Intent(Observation_NODE_NEW.this, HomeActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		//Button to get back to the sensordrone inputs
		Button previousSensordrone = (Button) findViewById(R.id.previousSensordrone);
		
		//check the value, if true, don't show the button
		if(!isSequential){
			environmentalInput.setVisibility(View.INVISIBLE);
			previousSensordrone.setVisibility(View.INVISIBLE);
		}
		
		previousSensordrone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
	}
		
		

	@Override
    public void onPause() {
        super.onPause();

        DefaultNotifier.instance().removeOxaListener(this);
        if(oxa!=null)
        { //Turn off oxa
        oxa.stopSensor();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Register Oxa Listener
        DefaultNotifier.instance().addOxaListener(this);

        NodeDevice node = asthmaApp.getActiveNode();
        if(node != null)
        {
            oxa = node.findSensor(NodeEnums.ModuleType.OXA);
            oxa.startSensor();
        }
    }
	
    @Override
    public void onOxaBaselineUpdate(OxaSensor sensor, final SensorReading<Float> baseline_reading) {
       Log.d("OXAUPDATE", baseline_reading.getValue() + "");
       mHandler.obtainMessage(Constants.MESSAGE_OXA_BASELINE_A,baseline_reading.getValue()).sendToTarget();
    }

    @Override
    public void onOxaUpdate(OxaSensor sensor, SensorReading<Float> reading) {
    	   Log.d("OXAUPDATE_READING", reading.getValue() + "");
    	   Message m = mHandler.obtainMessage(Constants.MESSAGE_OXA_READING);
    	   float aFloat = reading.getValue();
	       m.getData().putFloat(Constants.FLOAT_VALUE_KEY, aFloat);

	        //This will find the difference
	       
	        double variation=Math.abs(aFloat-asthmaApp.previousNOReading);
	        /**********
	         * checking whether the value changed minimum of variation specified for  NO
	         * First two conditions check whether current value minimum varied compared with previous value
	         * Third condition check If the last insert of sensor value if it is more than 6 hours then we need to insert without considering the variation
	         */
	        Date date=new Date();
	        asthmaApp.current_timestamp=new Timestamp(date.getTime());
	        if(variation>Constants.variation_no || asthmaApp.NO_timestamp.getMinutes()!=asthmaApp.current_timestamp.getMinutes())
	        {
	            //Casting the float value to double value
	            double sensorValue=(double)aFloat;
	            //IF humidity varied more than the minimum variation defined for that then we need to insert this into database
	            //we adding the value to the observations database
	            addRowtoSensorVlauesTable(Constants.sensors.no.ordinal(),sensorValue);
	            //Storing the timestamp after inserting the NO reading into observations table for current session reference.
	            asthmaApp.NO_timestamp=asthmaApp.current_timestamp;
	            //we are storing the current value 
	            asthmaApp.previousNOReading=aFloat;
	            
	        }
	       m.sendToTarget();
    }

    private final Handler mHandler = new Handler(){
    private final DecimalFormat formatter = new DecimalFormat("0.00000");

     @Override
     public void handleMessage(Message message)
     {
        float value = message.getData().getFloat(Constants.FLOAT_VALUE_KEY);
        switch(message.what){
            case Constants.MESSAGE_OXA_READING:
                noText.setText(value + " ppm");
                break;
            case Constants.MESSAGE_OXA_BASELINE_A:
            	//This code will get the baseline reading when the NODE first starts taking readings
                //noText.setText(message.obj.toString());
                break;
        }
      }
    };
    
    
    private void createBatteryAlert(Context c){
    	AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("NODE Battery Low!");
        builder.setMessage("Please connect to a charger");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		});
        builder.create().show();
    }
   
    //endregion
}
