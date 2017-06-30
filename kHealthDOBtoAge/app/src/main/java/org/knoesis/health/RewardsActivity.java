package org.knoesis.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.knoesis.health.constants.Constants;
import org.knoesis.health.database.DatabaseSummaryHelper;

import java.io.IOException;

public class RewardsActivity extends KHealthAsthmaParentActivity {
	DatabaseSummaryHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rewards);
		
		ImageView coin1 = (ImageView) findViewById(R.id.ImageView12);
		ImageView coin2 = (ImageView) findViewById(R.id.ImageView27);
		ImageView coin3 = (ImageView) findViewById(R.id.ImageView11);

		ImageView coin4 = (ImageView) findViewById(R.id.ImageView03);
		ImageView coin5 = (ImageView) findViewById(R.id.ImageView01);
		ImageView coin6 = (ImageView) findViewById(R.id.ImageView04);

		ImageView coin7 = (ImageView) findViewById(R.id.imageView02);
		ImageView coin8 = (ImageView) findViewById(R.id.ImageView09);
		ImageView coin9 = (ImageView) findViewById(R.id.ImageView28);

		ImageView coin10 = (ImageView) findViewById(R.id.ImageView05);
		ImageView coin11 = (ImageView) findViewById(R.id.ImageView10);
		ImageView coin12 = (ImageView) findViewById(R.id.ImageView13);
		ImageView coin13 = (ImageView) findViewById(R.id.ImageView19);

		ImageView coin14 = (ImageView) findViewById(R.id.ImageView06);
		ImageView coin15 = (ImageView) findViewById(R.id.ImageView20);
		ImageView coin16 = (ImageView) findViewById(R.id.ImageView16);

		ImageView coin17 = (ImageView) findViewById(R.id.ImageView08);
		ImageView coin18 = (ImageView) findViewById(R.id.ImageView18);
		ImageView coin19 = (ImageView) findViewById(R.id.ImageView26);

		ImageView coin20 = (ImageView) findViewById(R.id.ImageView29);
		ImageView coin21 = (ImageView) findViewById(R.id.ImageView21);
		ImageView coin22 = (ImageView) findViewById(R.id.ImageView17);
		ImageView coin23 = (ImageView) findViewById(R.id.ImageView02);

		ImageView coin24 = (ImageView) findViewById(R.id.ImageView22);
		ImageView coin25 = (ImageView) findViewById(R.id.ImageView25);
		ImageView coin26 = (ImageView) findViewById(R.id.ImageView23);
		ImageView coin27 = (ImageView) findViewById(R.id.ImageView24);

		ImageView coin28 = (ImageView) findViewById(R.id.ImageView33);
		ImageView coin29 = (ImageView) findViewById(R.id.ImageView31);
		ImageView coin30 = (ImageView) findViewById(R.id.ImageView30);
		ImageView coin31 = (ImageView) findViewById(R.id.ImageView32);

		ImageView coin32 = (ImageView) findViewById(R.id.ImageView34);

		ImageView[] coins = { coin1, coin2, coin3, coin4, coin5, coin6, coin7,
				coin8, coin9, coin10, coin11, coin12, coin13, coin14, coin15,
				coin16, coin17, coin18, coin19, coin20, coin21, coin22, coin23,
				coin24, coin25, coin26, coin27, coin28, coin29, coin30, coin31,
				coin32 };
		// Get the database
		dbHelper = new DatabaseSummaryHelper(getApplicationContext());
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String user = ((AsthmaApplication) getApplication())
				.getCurrentLoggedInUser();
		long coin = 0;
		if(!dbHelper.isTableExists("questions_and_answers"))
		{
			showAlert();
			
		}
		else if(dbHelper.getCountOfAnsweredQuestionsPerDay(user)==0){
			
			
			showAlert();
			
			
		}
		else{
		
		
		coin = dbHelper.getCountOfAnsweredQuestionsPerDay(user);
		}
				
		TextView msg = (TextView)findViewById(R.id.textView2);
		
		SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_WORLD_READABLE); 
		
		msg.setText("Congratulations you earned "+ coin +" out of "+prefs.getInt("numberofdeploymentdays",0)+" stars");

		for (int i = 0; i < coin; i++) {

			coins[i].setVisibility(View.VISIBLE);

		}

	}
	private void showAlert(){
		AlertDialog alert = new AlertDialog.Builder(RewardsActivity.this).create();
		alert.setTitle("No Questions Answered! ");
		alert.setMessage("Start answering to get more stars");
		alert.setIcon(R.drawable.fillingcoins);
		alert.setButton(AlertDialog.BUTTON_POSITIVE, "Back", 
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),
								"Go to Record readings to complete the questionnaire", 
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(RewardsActivity.this, HomeActivity.class);
						startActivity(i);
						finish();
					}
			
		});
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rewards, menu);
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

	public void onClick(View v){
		switch(v.getId()){
			case R.id.btn_finish:
				//Intent nextActivity = new Intent(RewardsActivity.this, HomeActivity.class);
				finish();
				//startActivity(nextActivity);
				break;
			default:
				Log.e("ACTIVITY:Rewards", "Unregistered button used. View: " + v.toString() +
						"ID:" + v.getId());
		}
	}

}
