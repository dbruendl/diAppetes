package com.example.diappetes.observer;

import android.widget.ProgressBar;

import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Report;

import lombok.RequiredArgsConstructor;

/**
 * Observes a percentage float value and updates a progress bar accordingly
 */
@RequiredArgsConstructor
public class ProgressBarStepGoalObserver implements Observer<Report> {
    private final ProgressBar progressBar;
    private final int stepGoal;

    @Override
    public void onChanged(Report report) {
        if(report == null) return;

        float progress = report.progress(stepGoal);

        progressBar.setProgress((int) (progress * 100));
    }
}
