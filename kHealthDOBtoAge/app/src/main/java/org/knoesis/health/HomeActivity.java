package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import org.knoesis.health.adapters.HomeIconAdapter;
import org.knoesis.health.dataHolders.HomeIconDataHolder;

import java.util.ArrayList;


/**********************************************
 * In this Home activity adding the Home screen Icons and creating the
 * OnclikListers() for those icons.
 *
 * @author Marupudi Surendra
 * @author Dan Vanuch
 */

public class HomeActivity extends KHealthAsthmaParentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting the content view to Home layout.
        setContentView(R.layout.activity_home);

        //Calling BG service for Email

//        Intent i = new Intent(getApplicationContext(), org.knoesis.health.NewBGService.class);
//        getApplicationContext().startService(i);


        // This method call will get the current name of the user that is logged
        // in
        // and display a welcome message to the user with their name
        TextView userName = (TextView) findViewById(R.id.userName);
        String user = ((AsthmaApplication) getApplication())
                .getCurrentLoggedInUser();
        userName.setText(" Welcome, " + user);

        // adding Home icons to gridview data object
        ArrayList<HomeIconDataHolder> data = new ArrayList<HomeIconDataHolder>();
        //Record Readings
        data.add(new HomeIconDataHolder(R.drawable.blue_heart, R.string.userInputs));
        //Summarization
        data.add(new HomeIconDataHolder(R.drawable.final_summary, R.string.summary));
        //Rewards
        data.add(new HomeIconDataHolder(R.drawable.rewards, R.string.rewards));
        //Help Activity for drop down menu
        data.add(new HomeIconDataHolder(R.drawable.blue_help, R.string.connection_options_help));
        //Settings
        data.add(new HomeIconDataHolder(R.drawable.settings, R.string.Settings));

        // TODO: Uncomment the following line to re-enable Individual Sensor Readings
        //data.add(new HomeIconDataHolder(R.drawable.blue_ind_readings,R.string.individualReadings));

        // data.add(new HomeIconDataHolder(R.drawable.ic_wheeze_recording,R.string.record_wheezing));
        // data.add(new HomeIconDataHolder(R.drawable.ic_connection_options,R.string.connection_options));
        //data.add(new HomeIconDataHolder(R.drawable.blue_chart,R.string.past_observations));
        // Analysis is being taken out as of now for the trial run
        // data.add(new HomeIconDataHolder(R.drawable.ic_analytics, R.string.analysis));
        // data.add(new HomeIconDataHolder(R.drawable.ic_person,R.string.Personal_Information));
        //data.add(new HomeIconDataHolder(R.drawable.blue_change_user,R.string.changeUser));

        // load icons into gridview
        GridView gridview = (GridView) findViewById(R.id.mainIconGridView);
        gridview.setAdapter(new HomeIconAdapter(data, this));

        // adding the setOnItemClickListener() to invoke the particular activity
        // based on user choice.
        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Position 0 is for Questionnaire activity
                if (position == 0) {

                    Intent i;
                    i = new Intent(HomeActivity.this, DayNightCurrentMenu.class);
                    startActivity(i);
                }// end of if

                if (position == 1) {
                    Intent i = new Intent(HomeActivity.this, GraphSelectionActivity.class);
                    startActivity(i);
                }// end of if

                if (position == 2) {
                    Intent i = new Intent(HomeActivity.this, RewardsActivity.class);
                    startActivity(i);


                }// end of if

                // Position 3 Help Activity
                if (position == 3) {
                    menu.dismiss();
                    Intent i = new Intent(HomeActivity.this, HelpActivity.class);
                    startActivity(i);

                }// end of if

                // // Position 4 is for Settings activity
                if (position == 4) {
                    Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(i);
                }// end of if

                // Position 1 is for Observations activity
                // TODO: Uncomment the following line to re-enable Individual Sensor Readings
                /*if (position == 5) {
                    Intent i = new Intent(HomeActivity.this,
							ObservationsActivity.class);

					startActivity(i);

				}// end of if*/

                // Position 3 Help Activity
                /*if (position == 2) {

					Intent i = new Intent(HomeActivity.this,
							GraphSelectionActivity.class);
					startActivity(i);

				}*/// end of if

                // The analytics is being taken out as of now in order to do the
                // trial run
                // Position 4 Help Activity
                // if (position == 3) {
                //
                // Intent i = new Intent(HomeActivity.this,
                // AnalyticsActivity.class);
                // startActivity(i);
                //
                //
                // }// end of if
                /*if (position == 4) {

					Intent i = new Intent(HomeActivity.this,
							ChangeUserActivity.class);
					startActivity(i);

				}*/// end of if

            }

        });



    }


}