package com.example.diappetes;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.diappetes.main.StepDetectorSensorEventListenerImpl;
import com.example.diappetes.main.StepListener;
import com.example.diappetes.persistence.model.UserDao;

import javax.inject.Inject;

public class StepTrackerService extends IntentService {

    public static final String UID_INTENT_KEY = "UID";
    public static final String SERVICE_NAME = "stepTrackerService";

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

        StepListener updateUserStepListener = new UpdateDatabaseStepListener(uid, userDao);
        stepDetectorSensorEventListener = new StepDetectorSensorEventListenerImpl();
        stepDetectorSensorEventListener.registerListener(updateUserStepListener);

        sensorManager.registerListener(stepDetectorSensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
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

        if(hasStarted()) {
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
