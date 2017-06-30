package org.knoesis.health;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.variable.framework.android.bluetooth.BluetoothService;
import com.variable.framework.android.bluetooth.DefaultBluetoothDevice;
import com.variable.framework.dispatcher.DefaultNotifier;
import com.variable.framework.node.AndroidNodeDevice;
import com.variable.framework.node.BaseSensor;
import com.variable.framework.node.NodeDevice;
import com.variable.framework.node.OxaSensor;
import com.variable.framework.node.enums.NodeEnums.ModuleType;

import org.knoesis.health.constants.Constants;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

/*********************************************************************************************
 * 
 * @author Marupudi Surendra Brahma
 * 
 *         In this class the code to connect and disconnect the NODE options
 *         implemented. In this jsut reused the code already implemented in the
 *         Observation_NODE_NO for connecting the NODE.
 * 
 **********************************************************************************************/
public class ConnectionNodeOptions extends KHealthAsthmaParentActivity implements NodeDevice.SensorDetector {
	
	    private static final String TAG = Observation_NODE_NEW.class.getName();
		private BluetoothService mService;
		private boolean isPulsing = false;
		private ProgressDialog mProgressDialog;
		private TextView noText;
		private OxaSensor oxa;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_node_options);
		Button optionsConnect = (Button) findViewById(R.id.options_connect);
		Button optionsDisconnect = (Button) findViewById(R.id.options_disconnect);
		//Button optionsReset = (Button) findViewById(R.id.options_reset);
		Button nextNodeOptions = (Button) findViewById(R.id.next_node_options);
		Button getBatteryLevel = (Button) findViewById(R.id.batteryLevel);
		DefaultNotifier.instance().addSensorDetectorListener(this);
		boolean isSequential = asthmaApp.getSequentialCheck();
		
		
		NodeDevice node = AsthmaApplication.getActiveNode(); 
		if(isSequential && node != null){
			if(node.isConnected()){
				Intent i = new Intent(ConnectionNodeOptions.this, Observation_NODE_NEW.class);
				startActivity(i);
                finish();
			}
		}
		
		if (!isSequential) {
			nextNodeOptions.setVisibility(View.INVISIBLE);
		}

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if((mBluetoothAdapter == null) || (!Constants.NODE_AVAILABLE)){
			Log.e("Node Connection", "Bluetooth unavailable");
			startActivity(new Intent(ConnectionNodeOptions.this, SpirometryCollection.class));
			finish();
		}else {
			ensureBluetoothIsOn();
			mService = new BluetoothService();
			AsthmaApplication.setServiceAPI(mService);


			optionsConnect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Retrieving the active NODE if present
					NodeDevice node = AsthmaApplication.getActiveNode();

					if (isNodeConnected(node)) {
						Toast.makeText(ConnectionNodeOptions.this, "Node already connected", Toast.LENGTH_SHORT)
								.show();
						return;
					} else {
						showPairedNodesDialog(ConnectionNodeOptions.this);

					}
				}
			});

			optionsDisconnect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Retrieving the active NODE if present
					NodeDevice node = AsthmaApplication.getActiveNode();
					/********************
					 * Checking whether node is already connected or not
					 */
					if (node == null || !node.isConnected()) {
						// If the node is not connected already
						Toast.makeText(getBaseContext(), "Node not connected",
								Toast.LENGTH_SHORT).show();
					} else {
						// if Node connected then disconnecting the NODE
						node.disconnect();
						asthmaApp.setActiveNode(null);
					}

				}
			});

			//		optionsReset.setOnClickListener(new OnClickListener() {
			//
			//			@Override
			//			public void onClick(View v) {
			//
			//				// Removing the all previously connected node devices.
			//				//db.removeAllPreviouslyConnectedSensors(Constants.nodeSensor);
			//				quickMessage("Successfully reset");
			//
			//			}
			//		});

			getBatteryLevel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DecimalFormat df = new DecimalFormat("0.0");
					NodeDevice node = asthmaApp.getActiveNode();
					if (node == null) {
						Toast.makeText(ConnectionNodeOptions.this, "Node not connected", Toast.LENGTH_SHORT).show();
					} else {
						double batteryLevel = (double) node.getBatteryLevel();
						//The highest value for the battery in volts is 4.2, in order to work, it must be above 3.5
						batteryLevel = ((batteryLevel - 3.5) / .7) * 100;
						if (batteryLevel <= 10.0) {
							Toast.makeText(ConnectionNodeOptions.this, "Please put NODE on charger", Toast.LENGTH_SHORT).show();
						}
						if (batteryLevel > 100) {
							Toast.makeText(ConnectionNodeOptions.this, "Battery level is fully charged", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(ConnectionNodeOptions.this, "Battery level is " + df.format(batteryLevel) + "%", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});

			nextNodeOptions.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(ConnectionNodeOptions.this, Observation_NODE_NEW.class);
					startActivity(i);
                    finish();

				}
			});





		/*
		if(node == null || !node.isConnected())
		 {

			//Instantiating the PreviouslyConnectedSensorsDataHolder list to hold the list of sensors information.
			List<PreviouslyConnectedSensorsDataHolder> sensorsList =db.getAllPreviouslyConnectedSensors(Constants.nodeSensor);
			if(sensorsList==null || sensorsList.size()==0)
			{
				asthmaApp.usedPreviousNodedeviceAddress=false;
			}
			else
			{
				 Set<BluetoothDevice> mBondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

				   for(BluetoothDevice device : mBondedDevices){
					  // if device already selected then break the loop
					   if(asthmaApp.usedPreviousNodedeviceAddress==true)
					   {
						   break;
					   }
					   //This will cross check with current available bluetooth devices with previously connected node devices
					  for(PreviouslyConnectedSensorsDataHolder sensor:sensorsList)
					  {
						//If address of the bluetooth device match with previously connected node device then break the comparison process and establish connection with that device
						if(device.getAddress().equals(sensor.getAddress()))
						{
							  //Assigning true because it using the previously connected node address
							  asthmaApp.usedPreviousNodedeviceAddress=true;
							  onDeviceSelected(device);
							  break;
						}
					  }

				   }

			}
			/**
			 * If the node is not connected using the previously connected node sensors then show the list of bluetooth sensors.
			 *
			if(asthmaApp.usedPreviousNodedeviceAddress==false){
				showPairedNodesDialog(this);
			}

		 }
		*/
		}
	}
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200) {
			ensureBluetoothIsOn();
		}
	}

	// endregion

	// region Private Methods

	/**
	 * Invokes a new intent to request to start the bluetooth, if not already
	 * on.
	 */
	private boolean ensureBluetoothIsOn() {
		if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(btIntent, 200);
			return false;
		}

		return true;
	}

	
	/**
	 * Determines if the node is connected. Null is permitted.
	 * 
	 * @param node
	 * @return
	 */
	public boolean isNodeConnected(NodeDevice node) {
		return node != null && node.isConnected();
	}

	// endregion

	// region Sensor Detector Callbacks

	public void onSensorConnected(NodeDevice nodeDevice, final BaseSensor baseSensor) {
		Log.d(TAG,"Sensor Found: " + baseSensor.getModuleType() + " SubType: " + baseSensor.getSubtype() + " Serial: " + baseSensor.getSerialNumber());
		if((baseSensor.getModuleType()).compareTo(ModuleType.OXA) == 0)
		Toast.makeText(ConnectionNodeOptions.this, baseSensor.getModuleType() + " has been detected", Toast.LENGTH_SHORT).show();
		asthmaApp.setActiveNode(nodeDevice);
	}

	public void onSensorDisconnected(NodeDevice nodeDevice,	final BaseSensor baseSensor) {
		Toast.makeText(ConnectionNodeOptions.this, baseSensor.getModuleType() + " has been removed", Toast.LENGTH_SHORT).show();
	}
	
	 /**
     * Shows a Dialog for any bonded device.
     *
     * Additionally, when item is select, onDeviceSelected is invoked.
     * @param c
     */
    protected void showPairedNodesDialog(Context c) {
        final Set<BluetoothDevice> mBondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        final String[] bluetoothNames =  new String[mBondedDevices.size()];
        int i=0;
        for(BluetoothDevice device :mBondedDevices)
        {
        	bluetoothNames[i++]=device.getName();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Select a NODE")
                .setItems(bluetoothNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                        int index=0;
                        for(BluetoothDevice device : mBondedDevices){
                        	// We only want NODE devices
        					
                            if(index++ == position){
                            	onDeviceSelected(device);                            	
        					}
                        }
                    }
                });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();


    }
    
   
    
    
    public void onDisconnect(NodeDevice node) {
        asthmaApp.setActiveNode(null); //Clear out NODE
        Toast.makeText(ConnectionNodeOptions.this, "Disconnected from " + node.getName(), Toast.LENGTH_SHORT).show();
       
    }

    
    public void onConnectionFailed(NodeDevice nodeDevice, Exception e) {
        Log.d(TAG, "=======onConnectionFailed=======");
        Toast.makeText(ConnectionNodeOptions.this, "Connection Failed to " + nodeDevice.getName(), Toast.LENGTH_SHORT).show();
    }

    
    public void nodeDeviceFailedToInit(NodeDevice nodeDevice) {
        Log.d(TAG, "=======nodeDeviceFailedToInit=======");
        Toast.makeText(ConnectionNodeOptions.this,"Failed to Initialize", Toast.LENGTH_SHORT).show();
            }

    
    public void onConnecting(NodeDevice node) {
        Log.d(TAG, "=======onConnecting=======");
        asthmaApp.setActiveNode(node);
        Toast.makeText(ConnectionNodeOptions.this, "Connecting to " + node.getName(), Toast.LENGTH_SHORT);
    }

    
    public void onCommunicationInitCompleted(NodeDevice nodeDevice) {
        Log.d(TAG, "=======onCommunicationInitCompleted=======");
        Toast.makeText(ConnectionNodeOptions.this, nodeDevice.getName() + " is ready to use", Toast.LENGTH_SHORT).show();
       
    }

    public void onConnected(NodeDevice node) {
        Log.d(TAG, "=======onConnected=======");
        Toast.makeText(ConnectionNodeOptions.this, "Initializing " + node.getName(), Toast.LENGTH_SHORT);
    }
    
    
    /**
     * Initiates a connection with the selected device.
     * @param device
     */
    private void onDeviceSelected(BluetoothDevice device)
    {
        Log.d("", "Selected Device Name" + device.getName());
        mService = AsthmaApplication.getService();
    	if(ensureBluetoothIsOn()) {
    		if(mService != null){
    		NodeDevice node = AndroidNodeDevice.getOrCreateNodeFromBluetoothDevice(device, new DefaultBluetoothDevice(mService));
    		changeNODEState(node);
    		}
        }
    }
    
    protected void changeNODEState(NodeDevice node){
        List<AndroidNodeDevice> nodes = AndroidNodeDevice.getManager().findByConnectionStatus(true);
        for(AndroidNodeDevice n : nodes)
        { if(!n.equals(node)) 
        	n.disconnect(); 
        }

        if(!node.isConnected()){
        	 node.connect();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();

        DefaultNotifier.instance().removeSensorDetectorListener(this);
       
    }

    @Override
    public void onResume() {
        super.onResume();

        //Register Oxa Listener
        DefaultNotifier.instance().addSensorDetectorListener(this);
    }

}
