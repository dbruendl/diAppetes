package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diappetes.databinding.ActivityStatBinding;
import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.main.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatActivity extends AppCompatActivity {
    Button homebtn4, infobtn4, petbtn4;
    private LineGraphSeries<DataPoint> series1, series2;

    ActivityStatBinding activityStatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStatBinding = ActivityStatBinding.inflate(getLayoutInflater());
        setContentView(activityStatBinding.getRoot());

        double x,y;

        x = 0;   // SIN GRAPH
        GraphView graphSteps = findViewById(R.id.graphSteps);
        series1 = new LineGraphSeries<>();
        int numDataPoints = 100;
        for(int i = 0; i < numDataPoints; i++){

            y = Math.sin(x);
            series1.appendData(new DataPoint(x,y), true, 100);
            x = x + 0.1;
        }
        series1.setColor(Color.BLUE);
        graphSteps.addSeries(series1);

        x = 0;   //COS GRAPH
        graphSteps.addSeries(series1);
        GraphView graphWeight = (GraphView) findViewById(R.id.graphWeight);
        series2 = new LineGraphSeries<>();
        int numDataPoints2 = 100;
        for(int i = 0; i < numDataPoints2; i++){


            y = Math.cos(x);
            series2.appendData(new DataPoint(x,y), true, 100);
            x = x + 0.1;
        }
        series2.setColor(Color.RED);
        graphWeight.addSeries(series2);

        activityStatBinding.navigationView.setSelectedItemId(R.id.tab_stat);
        activityStatBinding.navigationView.setOnNavigationItemSelectedListener(item -> {
            Intent startIntent;

            switch (item.getItemId()) {
                case R.id.tab_info:
                    startIntent = new Intent(getApplicationContext(), InfoActivity.class);
                    break;
                case R.id.tab_pet:
                    startIntent = new Intent(getApplicationContext(), PetActivity.class);
                    break;
                case R.id.tab_home:
                    startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    break;
                default:
                    return false;
            }

            startActivity(startIntent);

            return true;
        });
    }
}
