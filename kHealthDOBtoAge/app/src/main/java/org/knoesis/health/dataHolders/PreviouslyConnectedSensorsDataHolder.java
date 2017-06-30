package org.knoesis.health.dataHolders;
/*
 * This class will hold the information for the user's name, Date of Birth, gender.
 */
public class PreviouslyConnectedSensorsDataHolder {
		// member variable for the name
		private String name;
		//member variable for address
		private String address;

		// This is the constructor for the class
		public PreviouslyConnectedSensorsDataHolder(String name, String address ) {
			this.name = name;
			this.address=address;

		}
		// This is the constructor for the class
		public PreviouslyConnectedSensorsDataHolder(String address ) {
			this.address=address;

		}
				
		// This method returns the sensor name
		public String getName() {
			return this.name;
		}
		//This method returns the sensor address
		public String getAddress()
		{
			return this.address;
		}

		//This method set the sensor name
		public void  setName(String name){
			this.name=name;
		}
		//This method set the sensor address
		public void setAddress(String address)
		{
			this.address=address;
		}

}
