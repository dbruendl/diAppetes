package com.example.diappetes.observer;

import android.widget.TextView;

import androidx.lifecycle.Observer;

import lombok.RequiredArgsConstructor;

/**
 * Observes a number of total steps and updates a given text view with the total steps
 */
@RequiredArgsConstructor
public class TextViewStepGoalObserver implements Observer<Integer> {
    private final TextView textView;

    @Override
    public void onChanged(Integer totalSteps) {
        String text = "Steps taken " + totalSteps;

        textView.setText(text);
    }
}
