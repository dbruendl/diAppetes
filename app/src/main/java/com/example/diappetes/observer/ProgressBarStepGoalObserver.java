package com.example.diappetes.observer;

import android.widget.ProgressBar;

import androidx.lifecycle.Observer;

import lombok.RequiredArgsConstructor;

/**
 * Observes a percentage float value and updates a progress bar accordingly
 */
@RequiredArgsConstructor
public class ProgressBarStepGoalObserver implements Observer<Float> {
    private final ProgressBar progressBar;

    @Override
    public void onChanged(Float progress) {
        progressBar.setProgress((int) (progress * 100));
    }
}
