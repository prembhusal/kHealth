package org.knoesis.health.database;





public class activeSensors {
	String sensorName;
	int value;
	
	// Empty constructor
    public activeSensors(){
         
    }
    /**
     * 
     * This constructor will initialize the sensorName and value variables.
     * @param sensorName
     * @param value
     */
    public activeSensors( String sensorName, int value){
    	
        this.sensorName = sensorName;
        this.value = value;
    }
    
 // setting sensorName
    public void setSensorName(String sensorName){
    	this.sensorName = sensorName;
    }
    //getting the sensorName
    public String getSensorName(){
    	return sensorName;
    }
    
    //setting the value
    public void setValue(int value)
    {
    	this.value=value;
    }
    
    //getting the value
    public int getValue()
    {
    	return value;
    }
    
    
	
	
}
