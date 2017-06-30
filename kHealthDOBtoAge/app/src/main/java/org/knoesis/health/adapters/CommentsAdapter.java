package org.knoesis.health.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.knoesis.health.R;
import org.knoesis.health.dataHolders.CommentsDataHolder;

import java.util.List;

/************
 * Used to load comments and timestamps of comments into a list using an adapter.
 * 
 * @author Marupudi Surendra Brahma
 *
 */
public class CommentsAdapter  extends BaseAdapter{

	
	//declaring the arrayList of type CommentsDataHolder
	private List<CommentsDataHolder> data;
	private LayoutInflater lInflater;
	
	//constructor
	public CommentsAdapter(List<CommentsDataHolder> data, Context context) {
		this.data = data;
		//Instantiates a layout XML file into its corresponding View objects.
		lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}//end of constructor
	
	//This class holds the TextView and TextView components.
	private static class ViewHolder {
		private TextView comment;
		private TextView timeStamp;
	}//end of ViewHolder

	//@Override
	@Override
	public int getCount() {
		return data.size();
	}//end of getCount

	//@Override
	@Override
	public Object getItem(int arg0) {
		return null;
	}//end of getItem

	//@Override
	@Override
	public long getItemId(int arg0) {
		return 0;
	}//end of getItemId

	//@Override
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.comments, null);
			holder = new ViewHolder();
			holder.comment = (TextView) convertView.findViewById(R.id.commentTextView);
			holder.timeStamp = (TextView) convertView.findViewById(R.id.timeStampTextView);
			convertView.setTag(holder);
		}//end of if
		else {
			holder = (ViewHolder) convertView.getTag();
		}//end of else
		holder.comment.setText(data.get(position).getTimeStamp());
		holder.comment.setGravity(Gravity.LEFT);
		holder.timeStamp.setText(data.get(position).getComment());

		return convertView;
	}//end of getView

}//end of MainIconAdapter
