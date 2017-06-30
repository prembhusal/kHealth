package org.knoesis.health.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.knoesis.health.R;
import org.knoesis.health.dataHolders.HomeIconDataHolder;

import java.util.ArrayList;

/************
 * Provides an adapter to store and view the icons for activities.
 * 
 * @author Marupudi Surendra 
 *
 */

public class HomeIconAdapter extends BaseAdapter {
	
	//declaring the arrayList of type HomeIconDataHolder
	private ArrayList<HomeIconDataHolder> data;
	private LayoutInflater lInflater;
	
	//constructor
	public HomeIconAdapter(ArrayList<HomeIconDataHolder> data, Context context) {
		this.data = data;
		//Instantiates a layout XML file into its corresponding View objects.
		lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}//end of constructor
	
	//This class holds the ImageView and TextView components.
	private static class ViewHolder {
		private ImageView image;
		private TextView text;
	}//end of ViewHolder

	//@Override
	@Override
	public int getCount() {
		return data.size();
	}//end of getCount

	//@Override
	@Override
	public Object getItem(int arg0) {
		// Not used in this context
		return null;
	}//end of getItem

	//@Override
	@Override
	public long getItemId(int arg0) {
		// Not used in this context
		return 0;
	}//end of getItemId

	//@Override
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.main_icon, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.iconImageView);
			holder.text = (TextView) convertView.findViewById(R.id.iconTextView);
			convertView.setTag(holder);
		}//end of if
		else {
			holder = (ViewHolder) convertView.getTag();
		}//end of else
		holder.text.setText(data.get(position).getTextId());
		holder.text.setGravity(Gravity.CENTER);
		holder.image.setImageResource(data.get(position).getImageId());

		return convertView;
	}//end of getView

}//end of MainIconAdapter
