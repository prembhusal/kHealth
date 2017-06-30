package org.knoesis.health.dataHolders;


/********************************
 * 
 * @author Marupudi Surendra Brahma
 * 
 * This is used to hold the sensor values when we retrieve them from the database
 *
 */

public class SensorDataHolder {
	//Values to get from the every observation
	private double value;
	//Answers for the questions
	private int answer;
	//Comments
	private String comment;
	private String timestamp;
	//Variable to store date and time values
	public int day;
	public int month;
	public int year;
	private String date;
	private String time;
	public int hours;
	public int minutes;
	
	/********
	 * Constructor to initialize the values of observations data holder
	 * 
	 * @param value
	 * @param timestamp
	 */
	public SensorDataHolder(double value,String timestamp) {
		this.value=value;
		this.timestamp=timestamp;
	}
	/*************
	 * Constructor to initialize the answers data holder
	 * @param answer
	 * @param timestamp
	 */
	public SensorDataHolder(int answer,String timestamp) {
		this.answer=answer;
		this.timestamp=timestamp;
	}
	
	/*************
	 * Constructor to initialize the comments data holder
	 * 
	 * @param comment
	 * @param timestamp
	 */
	public SensorDataHolder(String comment,String timestamp) {
		this.comment=comment;
		this.timestamp=timestamp;
	}
	
	//Default constructor
	public SensorDataHolder()
	{
		
	}
	//To get the observation value
	public double getvalue() {
		return value;
	}
	//To set the observation value
	public void setValue(Double value) {
		this.value=value;
	}
	
	//To get the answer
	public int getAnswer() {
		return answer;
	}
	//To set the answer 
	public void setAnswer(int answer) {
		this.answer=answer;
	}
	//To get the comment
	public String getComment()
	{
		return comment;
	}
	//To set the comment
	public void setComment(String comment)
	{
		this.comment=comment;
	}
    //To get the Timestamp value
	public String getTimestamp() {
		return timestamp;
	}
	//To set the timestamp value
	public void setTimestamp(String timestamp) {
		this.timestamp=timestamp;
	}
	
	/****************
	 * this will extract the day,month,year,hours and minutes from the timestamp of observation
	 */
	public void splitTimestamp()
	{
		 
			//Splitting the whole timestamp into time and date
	        String[] dateArray =timestamp.split(" ");
	        //Retrieving date from time stamp
	        date = dateArray[0];
	        //Retrieving time from the timestamp
	        time=dateArray[1];
	        
	        //Again splitting the date using the delimiter -
	        dateArray = date.split("-");
	        //Parsing year from String
	        year = Integer.parseInt(dateArray[0]);
	        //Parsing month from String
	        month = Integer.parseInt(dateArray[1]);
	        //Parsing day from String 
	        day = Integer.parseInt(dateArray[2]);
	        
	        //Again splitting the time using the delimiter :
	        dateArray = time.split(":");
	        //Parsing the hours from string 
	        hours = Integer.parseInt(dateArray[0]);
	        //Parsing the minutes from String
	        minutes = Integer.parseInt(dateArray[1]);
	}
	
	
}
