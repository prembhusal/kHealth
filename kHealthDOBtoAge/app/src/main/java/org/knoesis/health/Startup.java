package org.knoesis.health;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class Startup extends Activity {
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		//startActivity(i);
		
		//Bouncing Animation
		ImageView logo = (ImageView)findViewById(R.id.image_logo);
		ObjectAnimator animY = ObjectAnimator.ofFloat(logo, "translationY", -100f, 0f);
		animY.setDuration(2500);
		animY.setInterpolator(new BounceInterpolator());
		animY.setRepeatCount(0);
		animY.start();



		
		Start aTask = new Start();
		aTask.execute();
		  
	}
	private class Start extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {
		
			//retrieving current time in milli  seconds.
			Long time = System.currentTimeMillis();
			
			
			while (System.currentTimeMillis() <= (time + 2000))
			{
			// empty loop for giving time to display splash screen.
			}
			
			// now go back to main activity
			 Intent i = new Intent(Startup.this,LoginScreenActivity.class);
			 startActivity(i);


			 
			Thread.yield();

			return true;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			finish();
		}
	}

	//background thread to trigger email service

	

}
