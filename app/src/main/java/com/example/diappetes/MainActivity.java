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

import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private static final String CHANNEL_ID = "69"; //nice
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Steps taken "; //Number of Steps:
    private int numSteps;
    private int goalSteps;
    public int progress;
    private TextView TvSteps;
    private ProgressBar pb;
    private ToggleButton tb;
    ImageView petmini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        petmini = findViewById(R.id.petmini);                //PET declaration
        petmini.setImageResource(R.drawable.neutralstatus);

        SentiloConnector sc = new SentiloConnector(this);

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
        simpleStepDetector.registerListener(this);
        pb = findViewById(R.id.pb_steps);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        tb = (ToggleButton) findViewById(R.id.toggleButton);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

                } else {
                    sensorManager.unregisterListener(MainActivity.this);
                }
            }
        });


        Button infobtn = (Button) findViewById(R.id.infobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent);

            }
        });

        Button petbtn = (Button) findViewById(R.id.petbtn);
        petbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PetActivity.class);

                String strName = progress + "";
                i.putExtra("STRING_I_NEED", strName);
                startActivity(i);
            }
        });

        Button statbtn = (Button) findViewById(R.id.statbtn);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        goalSteps = 10;
        // Average step is 0.74m and takes 0.5 sec (to verify)

        progress = (numSteps * 100) / goalSteps;

        pb.setProgress(progress);

        TvSteps.setText(TEXT_NUM_STEPS + numSteps);

        int petstatus;  // PET code is here because it needs to be updated

        if(progress>0){
            if(progress<100){ //0 - 100  During exercise
                petstatus = 1;
            } else { //100 - Exercise is finished
                petstatus = 2;
            }
        } else { //0 Exercise not started
            petstatus = 0;
        }


        switch (petstatus){
            case 1:
                petmini.setImageResource(R.drawable.happystatus);
                break;
            case 2:
                petmini.setImageResource(R.drawable.superhappystatus);
                break;
            case 0:
            default:
                petmini.setImageResource(R.drawable.neutralstatus);
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
