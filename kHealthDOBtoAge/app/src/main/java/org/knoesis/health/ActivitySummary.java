package org.knoesis.health;

import java.io.IOException;

import org.knoesis.health.database.DatabaseSummaryHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySummary extends KHealthAsthmaParentActivity {
	DatabaseSummaryHelper dbHelper;
	ListView lvUsers, lvChoice1;
	ListAdapter adapter1, adapter2;
	TextView tvNo1, tvNo2, tvYes1, tvYes2;
	TextView tvN1, tvN2, tvL1, tvL2, tvH1, tvH2, tvM1, tvM2;
	TextView tvLess2, tvGreat2;
	String currentUser;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		dbHelper = new DatabaseSummaryHelper(getApplicationContext());
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		 currentUser = ((AsthmaApplication) getApplication())
				.getCurrentLoggedInUser();

		tvNo1 = (TextView) findViewById(R.id.tvNoCount1);
		tvYes1 = (TextView) findViewById(R.id.tvYesCount1);
		tvNo2 = (TextView) findViewById(R.id.tvNoCount2);
		tvYes2 = (TextView) findViewById(R.id.tvYesCount2);
		tvN1 = (TextView) findViewById(R.id.tvN1);
		tvN2 = (TextView) findViewById(R.id.tvN2);
		tvL1 = (TextView) findViewById(R.id.tvL1);
		tvL2 = (TextView) findViewById(R.id.tvL2);
		tvH1 = (TextView) findViewById(R.id.tvH1);
		tvH2 = (TextView) findViewById(R.id.tvH2);
		tvM1 = (TextView) findViewById(R.id.tvM1);
		tvM2 = (TextView) findViewById(R.id.tvM2);
		tvLess2 = (TextView) findViewById(R.id.tvLess2);
		tvGreat2 = (TextView) findViewById(R.id.tvGreat2);
		
		if (!dbHelper.isTableExists("questions_and_answers")) {
			AlertDialog alertDialog = new AlertDialog.Builder(
					ActivitySummary.this).create();
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
							Intent i = new Intent(ActivitySummary.this,
									HomeActivity.class);
							startActivity(i);

						}
					});
			ScrollView sv = (ScrollView) findViewById(R.id.ScrollViewLayout);
			sv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(ActivitySummary.this,
							HomeActivity.class);
					startActivity(i);

				}

			});
			alertDialog.show();

		} else if (dbHelper.getAll(currentUser, 1, 0) == 0
				&& dbHelper.getAll(currentUser, 1, 1) == 0
				&& dbHelper.getAll(currentUser, 2, 0) == 0
				&& dbHelper.getAll(currentUser, 2, 1) == 0
				&& dbHelper.getAll(currentUser, 3, 0) == 0
				&& dbHelper.getAll(currentUser, 3, 1) == 0
				&& dbHelper.getAll(currentUser, 3, 2) == 0
				&& dbHelper.getAll(currentUser, 3, 3) == 0
				&& dbHelper.getAll(currentUser, 4, 0) == 0
				&& dbHelper.getAll(currentUser, 4, 1) == 0
				&& dbHelper.getAll(currentUser, 4, 2) == 0
				&& dbHelper.getAll(currentUser, 4, 3) == 0
				&& dbHelper.getAll2(currentUser, 5, "<", 2) == 0
				&& dbHelper.getAll2(currentUser, 5, ">", 2) == 0) {
			AlertDialog alertDialog = new AlertDialog.Builder(
					ActivitySummary.this).create();
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
							Intent i = new Intent(ActivitySummary.this,
									HomeActivity.class);
							startActivity(i);

						}
					});
			ScrollView sv = (ScrollView) findViewById(R.id.ScrollViewLayout);
			sv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(ActivitySummary.this,
							HomeActivity.class);
					startActivity(i);

				}

			});
			alertDialog.show();

		}

		else {
			// Question 1
			int count = dbHelper.getAll(currentUser, 1, 0);
			tvNo1.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 1, 1);
			tvYes1.setText(Integer.toString(count));
			// Question 2
			count = dbHelper.getAll(currentUser, 2, 0);
			tvNo2.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 2, 1);
			tvYes2.setText(Integer.toString(count));
			// Question 3
			count = dbHelper.getAll(currentUser, 3, 0);
			tvN1.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 3, 1);
			tvL1.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 3, 2);
			tvH1.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 3, 3);
			tvM1.setText(Integer.toString(count));
			// Question 4
			count = dbHelper.getAll(currentUser, 4, 0);
			tvN2.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 4, 1);
			tvL2.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 4, 2);
			tvH2.setText(Integer.toString(count));
			count = dbHelper.getAll(currentUser, 4, 3);
			tvM2.setText(Integer.toString(count));
			// Question 4
			count = dbHelper.getAll2(currentUser, 5, "<", 2);
			tvLess2.setText(Integer.toString(count));
			count = dbHelper.getAll2(currentUser, 5, ">", 2);
			tvGreat2.setText(Integer.toString(count));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_summary, menu);
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
