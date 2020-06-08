package com.example.diappetes.tracker;

import androidx.lifecycle.MutableLiveData;

import com.example.diappetes.main.StepListener;

public interface StepGoalTracker extends StepListener {
    /**
     * @return progress in decimal percentage e.g. 0.2 == 20%
     */
    MutableLiveData<Float> progress();

    MutableLiveData<Integer> totalSteps();
}
