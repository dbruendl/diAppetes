package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener{
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private TextView TvSteps;
    private ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

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


        Button infobtn = (Button)findViewById(R.id.infobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent);
            }
        });

        Button petbtn = (Button)findViewById(R.id.petbtn);
        petbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent);
            }
        });

        Button statbtn = (Button)findViewById(R.id.statbtn);
        statbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent);
            }
        });








    }

    public void onDefaultToggleClick(View view) {
        Toast.makeText(this, "DefaultToggleClick",Toast.LENGTH_SHORT).show();
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
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
