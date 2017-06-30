package org.knoesis.health.dataHolders;

public class DataHolder {

	private int imageId, textId;
	
	public DataHolder(int imageId, int textId) {
		this.imageId = imageId;
		this.textId = textId;
	}
	public int getImageId() {
		return imageId;
	}
	public int getTextId() {
		return textId;
	}
}
