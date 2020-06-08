package com.example.diappetes;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.diappetes.main.MainActivity;

public class WalkReminderNotificationReceiver extends BroadcastReceiver {

    private final String LOG_TAG = getClass().getSimpleName();
    public static final String KEY_CHANNEL_ID = "CHANNEL_ID";
    public static final int NOTIFICATION_ID = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationChannelId = intent.getStringExtra(KEY_CHANNEL_ID);

        if (notificationChannelId == null) {
            Log.w(LOG_TAG, "Could not send walk reminder notification: No notificationChannelId given in starting intent");
            return;
        }

        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, notificationChannelId)
                .setContentTitle(context.getText(R.string.title))
                .setContentText(context.getText(R.string.description))
                .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID, notification);
    }
}
