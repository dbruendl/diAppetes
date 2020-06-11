package com.example.diappetes.observer;

import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Report;

import lombok.RequiredArgsConstructor;

/**
 * Observes a number of total steps and updates a given text view with the total steps
 */
@RequiredArgsConstructor
public class TextViewStepObserver implements Observer<Report> {
    private final TextView textView;

    @Override
    public void onChanged(Report report) {
        if(report == null) return;

        String text = "Steps taken " + report.steps;

        textView.setText(text);
    }
}
