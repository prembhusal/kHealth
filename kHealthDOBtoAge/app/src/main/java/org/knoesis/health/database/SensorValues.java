package org.knoesis.health.database;

import java.sql.Timestamp;

public class SensorValues {
	//this is used to store the timestamp
    private Timestamp timestamp;
    private int  sensorName;
    private Double sensorValue;
    /**
     * constructor with the 3 arguments to initialize the data.
     * @param timestamp
     * @param sensorName
     * @param sensorValue
     */
    public SensorValues(Timestamp timestamp, int sensorName, Double sensorValue) {
    	this.timestamp=timestamp;
    	this.sensorName=sensorName;
    	this.sensorValue=sensorValue;
	}
    
	
    
    //constructor without argument
    public  SensorValues()
    {
    	
    }
    //This  will set he timestamp
    public void setTimestamp(Timestamp timestamp)
    {
    	this.timestamp=timestamp;
    }
  //This  will get he timestamp
    public Timestamp getTimestamp()
    {
    	return timestamp;
    }
    //THis will set the sensor id
    public void setSensorName(int sensorName)
    {
    	this.sensorName=sensorName;
    	
    }
    //THis will get the sensor id
    public int getSensorName()
    {
    	return sensorName;
    	
    }
    //THis will set the sensor value
    public void setSensorValue(Double sensorValue)
    {
    	this.sensorValue=sensorValue;
    	
    }
    //THis will get the sensor name
    public Double getSensorValue()
    {
    	return sensorValue;
    	
    }

}
