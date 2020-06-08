package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.main.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatActivity extends AppCompatActivity {
    Button homebtn4, infobtn4, petbtn4;
    private LineGraphSeries<DataPoint> series1, series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        homebtn4 = findViewById(R.id.homebtn);
        petbtn4 = findViewById(R.id.petbtn);
        infobtn4 = findViewById(R.id.infobtn);

        double x,y;

        x = 0;   // SIN GRAPH
        GraphView graphSteps = (GraphView) findViewById(R.id.graphSteps);
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


        // Menu
        homebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent4);
            }
        });
        infobtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent4 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent4);
            }
        });
        petbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent4 = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent4);
            }
        });



    }

}
