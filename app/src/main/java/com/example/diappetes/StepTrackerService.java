package com.example.diappetes;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.diappetes.main.MainActivity;
import com.example.diappetes.main.StepDetectorSensorEventListenerImpl;
import com.example.diappetes.main.StepListener;
import com.example.diappetes.persistence.model.UserDao;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Creates and registers a {@link UpdateDatabaseStepListener} to a
 * {@link StepDetectorSensorEventListenerImpl} which is registered on a {@link SensorManager} and
 * creates an infinite loop to be able to wait for {@link android.hardware.SensorEvent}'s
 * indefinitely.
 *
 * This service is started in the foreground using {@link #startForeground(int, Notification)}
 * showing a notification to indicate the app is tracking a users steps.
 */
@AndroidEntryPoint
public class StepTrackerService extends IntentService {

    public static final String UID_INTENT_KEY = "UID";
    public static final String NOTIFICATION_CHANNEL_INTENT_KEY = "NOTIFICATION_CHANNEL_ID";
    public static final String SERVICE_NAME = "stepTrackerService";
    private static final int SERVICE_ID = 35325;

    @Inject
    public UserDao userDao;

    private SensorManager sensorManager;
    private Sensor sensor;

    private StepDetectorSensorEventListenerImpl stepDetectorSensorEventListener;

    public StepTrackerService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;

        String uid = intent.getStringExtra(UID_INTENT_KEY);
        String notificationChannelId = intent.getStringExtra(NOTIFICATION_CHANNEL_INTENT_KEY);

        // TODO: just returning without any logging or exception or sth?
        if (uid == null || notificationChannelId == null) return;

        StepListener updateUserStepListener = new UpdateDatabaseStepListener(uid, userDao);
        stepDetectorSensorEventListener = new StepDetectorSensorEventListenerImpl();
        stepDetectorSensorEventListener.registerListener(updateUserStepListener);

        sensorManager.registerListener(stepDetectorSensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                21093750,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, notificationChannelId)
                .setContentText(getText(R.string.step_tracker_notification_title))
                .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build();

        startForeground(SERVICE_ID, notification);

        while (true) ;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (hasStarted()) {
            sensorManager.unregisterListener(stepDetectorSensorEventListener);
        }
    }

    private boolean hasStarted() {
        return stepDetectorSensorEventListener != null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
