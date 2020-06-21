package com.example.diappetes.observer;

import android.graphics.Color;

import androidx.lifecycle.Observer;

import com.example.diappetes.DateUtils;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserReports;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserReportsLineChartObserver implements Observer<UserReports> {

    private final LineChart lineChart;

    public UserReportsLineChartObserver(LineChart lineChart) {
        this.lineChart = lineChart;

        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long) value);

                return new SimpleDateFormat("MM/dd", Locale.ENGLISH).format(date);
            }
        });
    }

    @Override
    public void onChanged(UserReports userReports) {
        if (userReports == null) return;

        List<Entry> stepEntries = new ArrayList<>();

        Collections.sort(userReports.reports, (report1, report2) -> report1.created.compareTo(report2.created));

        for (Report report : userReports.reports) {
            float dateInMs = DateUtils.getStartOfDayMillis(report.created);
            float steps = report.steps;

            stepEntries.add(new Entry(dateInMs, steps));
        }

        LineDataSet stepsDataSet = new LineDataSet(stepEntries, "Steps taken");

        LineData stepsLineData = new LineData(stepsDataSet);
        stepsLineData.setValueTextColor(Color.WHITE);

        lineChart.setData(stepsLineData);
        lineChart.invalidate();
    }
}
