package com.example.diappetes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.StatisticsBinding;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsFragment extends Fragment {
    Button homebtn4, infobtn4, petbtn4;
    private LineGraphSeries<DataPoint> series1, series2;

    StatisticsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StatisticsBinding.inflate(inflater, container, false);

        double x,y;

        x = 0;   // SIN GRAPH
        series1 = new LineGraphSeries<>();
        int numDataPoints = 100;
        for(int i = 0; i < numDataPoints; i++){

            y = Math.sin(x);
            series1.appendData(new DataPoint(x,y), true, 100);
            x = x + 0.1;
        }
        series1.setColor(Color.BLUE);
        binding.graphSteps.addSeries(series1);

        x = 0;   //COS GRAPH
        binding.graphSteps.addSeries(series1);
        series2 = new LineGraphSeries<>();
        int numDataPoints2 = 100;
        for(int i = 0; i < numDataPoints2; i++){


            y = Math.cos(x);
            series2.appendData(new DataPoint(x,y), true, 100);
            x = x + 0.1;
        }
        series2.setColor(Color.RED);
        binding.graphWeight.addSeries(series2);

        return binding.getRoot();
    }
}
