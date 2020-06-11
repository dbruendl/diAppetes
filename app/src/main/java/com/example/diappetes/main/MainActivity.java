package com.example.diappetes.main;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.Volley;
import com.example.diappetes.BatteryStatusChangedReceiver;
import com.example.diappetes.PetActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatActivity;
import com.example.diappetes.StepTrackerService;
import com.example.diappetes.WalkReminderNotificationReceiver;
import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.login.LoginViewModel;
import com.example.diappetes.observer.PetStepGoalObserver;
import com.example.diappetes.observer.ProgressBarStepGoalObserver;
import com.example.diappetes.observer.TextViewStepGoalObserver;
import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.sentilo.SentiloConnector;
import com.example.diappetes.sentilo.SentiloConnectorVolleyImpl;
import com.example.diappetes.sentilo.SentiloUpdateService;
import com.example.diappetes.tracker.SimpleStepGoalTrackerImpl;
import com.example.diappetes.tracker.StepGoalTracker;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.diappetes.WalkReminderNotificationReceiver.KEY_CHANNEL_ID;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    public AppDatabase appDatabase;

    @Inject
    public LoginViewModel loginViewModel;

    private final static String SENTILO_IDENTITY_KEY = "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475";
    private final static String CHANNEL_ID = "69"; //nice
    private final static long GO_FOR_WALK_NOTIFICATION_INTERVAL_IN_MS = 18 /* h */ * 60 /* m */ * 60 /* s */ * 1000 /* ms */;
    private Calendar calendar = Calendar.getInstance();

    private StepDetectorSensorEventListenerImpl simpleStepDetector;
    private SensorManager sensorManager;
    private int goal = 10;
    private int send = 3;
    private Sensor accel;
    public int progress;
    private TextView textViewTotalSteps;
    private ProgressBar stepGoalProgressBar;
    private ToggleButton toggleStepTrackingButton;
    ImageView imageViewPetMini;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SentiloConnector sentiloConnector = new SentiloConnectorVolleyImpl(Volley.newRequestQueue(this), SENTILO_IDENTITY_KEY);
        StepGoalTracker stepGoalTracker = new SimpleStepGoalTrackerImpl(goal);

        SentiloUpdateService sentiloUpdateService = new SentiloUpdateService(sentiloConnector, send);
        stepGoalTracker.totalSteps().observe(this, sentiloUpdateService);

        imageViewPetMini = findViewById(R.id.petmini);
        imageViewPetMini.setImageResource(R.drawable.neutralstatus);
        PetStepGoalObserver petStepGoalObserver = new PetStepGoalObserver(imageViewPetMini);
        stepGoalTracker.progress().observe(this, petStepGoalObserver);

        stepGoalProgressBar = findViewById(R.id.pb_steps);
        ProgressBarStepGoalObserver progressBarStepGoalObserver = new ProgressBarStepGoalObserver(stepGoalProgressBar);
        stepGoalTracker.progress().observe(this, progressBarStepGoalObserver);

        textViewTotalSteps = findViewById(R.id.tv_steps);
        TextViewStepGoalObserver textViewStepGoalObserver = new TextViewStepGoalObserver(textViewTotalSteps);
        stepGoalTracker.totalSteps().observe(this, textViewStepGoalObserver);

        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        final Intent intent = new Intent(this, WalkReminderNotificationReceiver.class)
                .putExtra(KEY_CHANNEL_ID, CHANNEL_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2135323, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetectorSensorEventListenerImpl();
        simpleStepDetector.registerListener(stepGoalTracker);

        toggleStepTrackingButton = findViewById(R.id.toggleButton);

        toggleStepTrackingButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent startStepTrackerServiceIntent = new Intent(this, StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, "t")
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, CHANNEL_ID);
                startService(startStepTrackerServiceIntent);

                stepGoalProgressBar.setVisibility(View.VISIBLE);
                textViewTotalSteps.setVisibility(View.VISIBLE);

                sensorManager.registerListener(simpleStepDetector, accel, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Intent stopStepTrackerServiceIntent = new Intent(this, StepTrackerService.class);

                stopService(stopStepTrackerServiceIntent);

                stepGoalProgressBar.setVisibility(View.GONE);
                textViewTotalSteps.setVisibility(View.GONE);
                sensorManager.unregisterListener(simpleStepDetector);
            }
        });

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryStatusChangedReceiver bscr = new BatteryStatusChangedReceiver(sentiloConnector);
        registerReceiver(bscr, ifilter);

        Button infobtn = findViewById(R.id.infobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent);

            }
        });

        Button petbtn = findViewById(R.id.petbtn);
        petbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PetActivity.class);

                String strName = progress + "";
                i.putExtra("STRING_I_NEED", strName);
                startActivity(i);
            }
        });

        Button statbtn = findViewById(R.id.statbtn);
        statbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent);
            }
        });
    }

    public void onDefaultToggleClick(View view) {
        Toast.makeText(this, "DefaultToggleClick", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
