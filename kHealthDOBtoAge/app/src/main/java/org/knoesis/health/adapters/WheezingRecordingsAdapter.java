package org.knoesis.health.adapters;

import java.util.List;

import org.knoesis.health.R;
import org.knoesis.health.dataHolders.WheezingRecordingsDataHolder;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/************
 * 
 * @author Marupudi Surendra Brahma
 *
 */

public class WheezingRecordingsAdapter extends BaseAdapter{

	
	//declaring the arrayList of type WheezingRecordingsDataHolder
	private List<WheezingRecordingsDataHolder> data;
	private LayoutInflater lInflater;
	
	//constructor
	public WheezingRecordingsAdapter(List<WheezingRecordingsDataHolder> data, Context context) {
		this.data = data;
		//Instantiates a layout XML file into its corresponding View objects.
		lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}//end of constructor
	
	//This class holds the TextView component.
	private static class ViewHolder {
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
			convertView = lInflater.inflate(R.layout.wheezing_recordings_list, null);
			holder = new ViewHolder();
			holder.timeStamp = (TextView) convertView.findViewById(R.id.timeStampTextView);
			convertView.setTag(holder);
		}//end of if
		else {
			holder = (ViewHolder) convertView.getTag();
		}//end of else
		
		holder.timeStamp.setText(data.get(position).getTimeStamp());
		holder.timeStamp.setGravity(Gravity.LEFT);

		return convertView;
	}//end of getView

}//end of MainIconAdapter

