package com.example.diappetes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.toolbox.Volley;
import com.example.diappetes.observer.PetStepGoalObserver;
import com.example.diappetes.observer.ProgressBarStepGoalObserver;
import com.example.diappetes.observer.TextViewStepGoalObserver;
import com.example.diappetes.sentilo.SentiloConnector;
import com.example.diappetes.sentilo.SentiloConnectorVolleyImpl;
import com.example.diappetes.sentilo.SentiloUpdateService;
import com.example.diappetes.tracker.SimpleStepGoalTrackerImpl;
import com.example.diappetes.tracker.StepGoalTracker;

public class MainActivity extends AppCompatActivity {

    private final static String SENTILO_IDENTITY_KEY = "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475";
    private static final String CHANNEL_ID = "69"; //nice
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;

    private Sensor accel;
    public int progress;
    private TextView textViewTotalSteps;
    private ProgressBar stepGoalProgressBar;
    private ToggleButton toggleStepTrackingButton;
    ImageView imageViewPetMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SentiloConnector sentiloConnector = new SentiloConnectorVolleyImpl(Volley.newRequestQueue(this), SENTILO_IDENTITY_KEY);
        StepGoalTracker stepGoalTracker = new SimpleStepGoalTrackerImpl(10);

        SentiloUpdateService sentiloUpdateService = new SentiloUpdateService(sentiloConnector, 3);
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

        // Create an explicit intent for an Activity in your app
        final Intent intent = new Intent(this, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.circle)
                .setContentTitle("Hey take care of your pet")
                .setContentText("The day is nearly over and your pet is not Happy yet")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(stepGoalTracker);

        toggleStepTrackingButton = findViewById(R.id.toggleButton);

        toggleStepTrackingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewTotalSteps.setVisibility(View.VISIBLE);
                    sensorManager.registerListener(simpleStepDetector, accel, SensorManager.SENSOR_DELAY_FASTEST);
                } else {
                    textViewTotalSteps.setVisibility(View.GONE);
                    sensorManager.unregisterListener(simpleStepDetector);
                }
            }
        });

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
            CharSequence name = getString(R.string.channel_notify);
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
