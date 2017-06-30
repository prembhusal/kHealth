package org.knoesis.health;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.knoesis.health.constants.Constants;
import org.knoesis.health.database.DatabaseSummaryHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Vaikunth on 3/17/2017.
 */

public class EmailSendingReceiver extends BroadcastReceiver {
    public void execute(Context context) {


        DatabaseSummaryHelper dbHelper;

        Log.d("Email Service", "  Started");

        // Get the database
        dbHelper = new DatabaseSummaryHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME,
                context.MODE_WORLD_READABLE);
        String lastLoggedUser = prefs.getString("Last User logged In", null);


        if (dbHelper.dailyEmailCheck2(Integer.parseInt(prefs.getString("numberofdaysunanswered", "1")), lastLoggedUser).equals("success")) {
            try {
                String to_address = prefs.getString("emailTo", "vaikunth@knoesis.org");
                Log.w("TO Address", "" + to_address);
                String cc_address = prefs.getString("ccTo", "vaikunth@knoesis.org");
                Log.w("CC Address", "" + cc_address);
                String textMessage = prefs.getString("Message", "User" + lastLoggedUser + "has not answered the questions for today");
                Log.w("Message", "" + textMessage);
                String subject = prefs.getString("subject", "Subject --- ") + "\t" + new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
                Log.w("Subject", "" + subject);
                Log.d("something", " calling mailgun");
                /*
                Using MailGun API in an UI Background Thread, to avoid NetworkOnMainThread Exception
                 */
                MailGun mg = new MailGun(to_address, cc_address, textMessage, subject);
                mg.start();


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Log.d("Run", "run working");


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("hitting the receiver", " every at 23 45pm");
        execute(context.getApplicationContext());
    }


}
