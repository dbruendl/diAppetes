package com.example.diappetes.observer;

import android.widget.ProgressBar;

import androidx.lifecycle.Observer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProgressBarStepGoalObserver implements Observer<Float> {
    private final ProgressBar progressBar;

    @Override
    public void onChanged(Float progress) {
        progressBar.setProgress((int) (progress * 100));
    }
}
