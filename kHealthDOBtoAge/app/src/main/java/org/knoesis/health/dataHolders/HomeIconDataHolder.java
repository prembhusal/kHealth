package org.knoesis.health.dataHolders;

/************
 * 
 * It holds the data required to populate the home screen icons.
 * Each Icon has it's own image and Id so we are using the two variables imageId and textId.
 * And also we are defined the property functions to retrieve the property values of imageId and textId.
 * 
 * 
 * @author Marupudi Surendra 
 *
 */


public class HomeIconDataHolder {

	private int imageId, textId;
	
	public HomeIconDataHolder(int imageId, int textId) {
		this.imageId = imageId;
		this.textId = textId;
	}
	//To retrieve imageId of specific Icon.
	public int getImageId() {
		return imageId;
	}
	//To retrieve the textId of specific Icon.
	public int getTextId() {
		return textId;
	}
}
