package com.example.diappetes.observer;

import android.os.Build;
import android.text.PrecomputedText;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import com.example.diappetes.DateUtils;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserReports;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserReportsLineChartObserver implements Observer<UserReports> {

    private final LineChart lineChart;

    public UserReportsLineChartObserver(LineChart lineChart) {
        this.lineChart = lineChart;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onChanged(UserReports userReports) {
        List<Entry> stepEntries = new ArrayList<>();

        Collections.sort(userReports.reports, (report1, report2) -> report1.created.compareTo(report2.created));

        for (Report report : userReports.reports) {
            float dateInMs = DateUtils.getStartOfDayMillis(report.created);
            float steps = report.steps;

            stepEntries.add(new Entry(dateInMs, steps));
        }

        LineDataSet stepsDataSet = new LineDataSet(stepEntries, "Steps taken");
        LineData stepsLineData = new LineData(stepsDataSet);

        lineChart.setData(stepsLineData);
        lineChart.invalidate();
    }
}
