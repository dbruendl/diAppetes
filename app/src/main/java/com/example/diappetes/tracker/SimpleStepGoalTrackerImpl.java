package com.example.diappetes.tracker;

import androidx.lifecycle.MutableLiveData;

import com.example.diappetes.main.StepListener;

import lombok.Getter;

@Getter
public class SimpleStepGoalTrackerImpl implements StepGoalTracker, StepListener {
    private final int stepGoal;
    private final MutableLiveData<Float> progress;

    private int totalSteps;

    public SimpleStepGoalTrackerImpl(int stepGoal) {
        this.stepGoal = stepGoal;
        this.progress = new MutableLiveData<>();
        this.totalSteps = 0;
    }

    @Override
    public void step() {
        totalSteps++;

        calculateProgress();
    }

    private void calculateProgress() {
        float progress = (float) totalSteps / stepGoal;

        this.progress.postValue(progress);
    }

    @Override
    public MutableLiveData<Float> progress() {
        return progress;
    }
}
