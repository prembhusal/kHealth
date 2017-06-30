package org.knoesis.health.dataHolders;
/************
 * 
 * It holds the data required to populate Wheezing Recordings display activity.
 * Each TimeStamp has its own Wheezing Recording to refer.
 * 
 * 
 * @author Marupudi Surendra Brahma
 *
 */
public class WheezingRecordingsDataHolder {

	//This will hold the address of the Wheezing Recording
	private String Address;
	private String timeStamp;
	
	public WheezingRecordingsDataHolder(String timeStamp, String Address) {
		this.timeStamp = timeStamp;
		this.Address = Address;
	}
	//To retrieve TimeStamp of specific Wheezing Recording.
	public String getTimeStamp() {
		return timeStamp;
	}
	//To retrieve the Address.
	public String getAddress() {
		return Address;
	}
}
