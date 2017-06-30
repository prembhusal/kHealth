package org.knoesis.health;

import java.io.IOException;

import org.knoesis.health.database.DatabaseSummaryHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Current_Summary extends KHealthAsthmaParentActivity {
	DatabaseSummaryHelper dbHelper;
	TextView tvCough1, tvWheeze1, tvChest1, tvBreath1, tvAlbuterol1;
	TextView tvCough2, tvWheeze2, tvChest2, tvBreath2, tvAlbuterol2;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current__summary);
		dbHelper = new DatabaseSummaryHelper(getApplicationContext());
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String user = ((AsthmaApplication) getApplication())
				.getCurrentLoggedInUser();

		tvCough1 = (TextView) findViewById(R.id.tvCough1);
		tvWheeze1 = (TextView) findViewById(R.id.tvWheeze1);
		tvChest1 = (TextView) findViewById(R.id.tvChest1);
		tvBreath1 = (TextView) findViewById(R.id.tvBreath1);
		tvAlbuterol1 = (TextView) findViewById(R.id.tvAlbuterol1);
		tvCough2 = (TextView) findViewById(R.id.tvCough2);
		tvWheeze2 = (TextView) findViewById(R.id.tvWheeze2);
		tvChest2 = (TextView) findViewById(R.id.tvChest2);
		tvBreath2 = (TextView) findViewById(R.id.tvBreath2);
		tvAlbuterol2 = (TextView) findViewById(R.id.tvAlbuterol2);
		if(!dbHelper.isTableExists("questions_and_answers")){
			AlertDialog alertDialog = new AlertDialog.Builder(
					Current_Summary.this).create();
			alertDialog.setTitle("No Readings");
			alertDialog.setMessage(" There are no readings submitted !");
			alertDialog.setIcon(R.drawable.warning_alert);
			alertDialog.setButton("Back",
					new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog,
								final int which) {
							// Write your code here to execute after dialog
							// closed
							Toast.makeText(getApplicationContext(),
									" Please complete the questionnaire ",
									Toast.LENGTH_SHORT).show();
							Intent i = new Intent(Current_Summary.this,
									HomeActivity.class);
							startActivity(i);

						}
					});
			ScrollView sv = (ScrollView) findViewById(R.id.ScrollViewLayout);
			sv.setOnClickListener(new OnClickListener() {

			    @Override
			    public void onClick(View v) {
			    	Intent i = new Intent(Current_Summary.this,
							HomeActivity.class);
					startActivity(i);

			    }

			 });
			alertDialog.show();

			
								
		}
		else if (dbHelper.getAll(user, 7, 0) == 0
				&& dbHelper.getAll(user, 7, 1) == 0
				&& dbHelper.getAll(user, 8, 0) == 0
				&& dbHelper.getAll(user, 8, 1) == 0
				&& dbHelper.getAll(user, 9, 0) == 0
				&& dbHelper.getAll(user, 9, 1) == 0
				&& dbHelper.getAll(user, 10, 0) == 0
				&& dbHelper.getAll(user, 10, 1) == 0
				&& dbHelper.getAll(user, 12, 0) == 0
				&& dbHelper.getAll(user, 12, 1) == 0)
				 {
			AlertDialog alertDialog = new AlertDialog.Builder(
					Current_Summary.this).create();
			alertDialog.setTitle("No Readings");
			alertDialog.setMessage(" There are no readings submitted !");
			alertDialog.setIcon(R.drawable.warning_alert);
			alertDialog.setButton("Back",
					new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog,
								final int which) {
							// Write your code here to execute after dialog
							// closed
							Toast.makeText(getApplicationContext(),
									" Please complete the questionnaire ",
									Toast.LENGTH_SHORT).show();
							Intent i = new Intent(Current_Summary.this,
									HomeActivity.class);
							startActivity(i);

						}
					});
			ScrollView sv = (ScrollView) findViewById(R.id.ScrollViewLayout);
			sv.setOnClickListener(new OnClickListener() {

			    @Override
			    public void onClick(View v) {
			    	Intent i = new Intent(Current_Summary.this,
							HomeActivity.class);
					startActivity(i);

			    }

			 });
			alertDialog.show();
		
						
		}	
		
		else {
			// Question 1
			int count = dbHelper.getAll(user,7, 0);
			tvCough1.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 7, 1);
			tvCough2.setText(Integer.toString(count));
			// Question 2
			count = dbHelper.getAll(user, 8, 0);
			tvWheeze1.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 8, 1);
			tvWheeze2.setText(Integer.toString(count));
			// Question 3
			count = dbHelper.getAll(user, 9, 0);
			tvChest1.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 9, 1);
			tvChest2.setText(Integer.toString(count));
			// Question 4
			count = dbHelper.getAll(user, 10, 0);
			tvBreath1.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 10, 1);
			tvBreath2.setText(Integer.toString(count));
			//Question 5 
			count = dbHelper.getAll(user,12, 0);
			tvAlbuterol1.setText(Integer.toString(count));
			count = dbHelper.getAll(user, 12, 1);
			tvAlbuterol2.setText(Integer.toString(count));
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current__summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
