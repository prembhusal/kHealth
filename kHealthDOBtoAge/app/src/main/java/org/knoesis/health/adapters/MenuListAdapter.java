package org.knoesis.health.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.knoesis.health.R;
import org.knoesis.health.dataHolders.DataHolder;

import java.util.ArrayList;

/**
 * Provides an adapter to display options in a menu list.
 */
public class MenuListAdapter extends BaseAdapter {

	private ArrayList<DataHolder> data;
	private LayoutInflater lInflater;

    /**
     * Constructor
     * @param data An ArrayList containing the images and classes for each entry.
     * @param context The context in which the menu list exists.
     */
	public MenuListAdapter(ArrayList<DataHolder> data, Context context) {
		this.data = data;
		lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}//end of constructor
	
	private static class ViewHolder {
		private ImageView image;
		private TextView text;
	}//end of ViewHolder

	@Override
	public int getCount() {
		return data.size();
	}//end of getCount

	@Override
	public Object getItem(int arg0) {
		// Not used in this context
		return null;
	}//end of getItem

	@Override
	public long getItemId(int arg0) {
		// Not used in this context
		return 0;
	}//end of getItemId

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.menu_icon, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.iconImageView);
			holder.text = (TextView) convertView.findViewById(R.id.iconTextView);
			convertView.setTag(holder);
		}//end of if
		else {
			holder = (ViewHolder) convertView.getTag();
		}//end of else
		holder.text.setText(data.get(position).getTextId());
		holder.image.setImageResource(data.get(position).getImageId());

		return convertView;
	}//end of getView

}//end of MainIconAdapter
