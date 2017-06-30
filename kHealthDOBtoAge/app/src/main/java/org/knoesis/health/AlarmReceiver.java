package org.knoesis.health;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
	

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		//Background service for sending email alerts when the Alarm goes off
		//Intent i = new Intent(context,org.knoesis.health.NewBGService.class);
		//context.startService(i);
		
		
		PendingIntent home = PendingIntent.getActivity(context, 100,
				new Intent(context, Startup.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		Uri soundNotification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		Notification.Builder builder = new Notification.Builder(context);
		builder.setSmallIcon(R.drawable.logo);
		builder.setContentTitle("kHealth Asthma Alert!");
		builder.setContentText("Take the survey and record readings!");
		builder.setSound(soundNotification, AudioManager.STREAM_ALARM);
		builder.setVibrate(new long[] { 2000, 2000 });
		builder.setContentIntent(home);

		builder.setAutoCancel(true);

		Notification notification = builder.build();

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify("Notification Service", 1, notification);
		
		
		
	}


}
