package org.knoesis.health.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.knoesis.health.AsthmaApplication;
import org.knoesis.health.R;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.List;

/**
 * Provides an adapter to list the users emphasizing the current user logged in.
 */
public class UserListAdapter extends BaseAdapter {
	
	private List<UserDataHolder> data;
	private LayoutInflater lInflater;
	private AsthmaApplication application;
	

	
	public UserListAdapter(List<UserDataHolder> data, Context context, AsthmaApplication application){
		this.data = data;
		this.application = application;
		lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

    /** Holds the TextView component.
     * 
     */
		private static class ViewHolder {
			private TextView userName;
			
			
		}//end of ViewHolder
	
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.users, null);
			holder = new ViewHolder();
			holder.userName = (TextView) convertView.findViewById(R.id.userName);
			convertView.setTag(holder);
		}//end of if
		else {
			holder = (ViewHolder) convertView.getTag();
		}//end of else
		
			String name = application.getCurrentLoggedInUser();
	
			if (data.get(position).getName().equalsIgnoreCase(name)) {
				holder.userName.setText(data.get(position).getName() + "  (Current User) ");
				holder.userName.setTextColor(Color.GREEN);
			}
			else{
				holder.userName.setText(data.get(position).getName());
			}
		
			holder.userName.setGravity(Gravity.LEFT);

		return convertView;
	}//end of getView
	
	
}


