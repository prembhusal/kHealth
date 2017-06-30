package org.knoesis.health.dataHolders;



/************
 * 
 * It holds the data required to populate comments display activity.
 * Each TimeStamp has its own comment to refer.
 * 
 * 
 * @author Marupudi Surendra Brahma
 *
 */
public class CommentsDataHolder {
	private String comment;
	private String timeStamp;
	
	public CommentsDataHolder(String timeStamp, String comment) {
		this.timeStamp = timeStamp;
		this.comment = comment;
	}
	//To retrieve TimeStamp of specific comment.
	public String getTimeStamp() {
		return timeStamp;
	}
	//To retrieve the comment.
	public String getComment() {
		return comment;
	}
}
