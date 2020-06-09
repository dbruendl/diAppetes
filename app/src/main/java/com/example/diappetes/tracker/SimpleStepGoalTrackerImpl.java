package com.example.diappetes.tracker;

import androidx.lifecycle.MutableLiveData;

import lombok.Getter;

@Getter
public class SimpleStepGoalTrackerImpl implements StepGoalTracker {
    private final int stepGoal;

    private final MutableLiveData<Float> progress;
    private final MutableLiveData<Integer> totalSteps;

    public SimpleStepGoalTrackerImpl(int stepGoal) {
        this.stepGoal = stepGoal;
        this.progress = new MutableLiveData<>();
        this.totalSteps = new MutableLiveData<>();
        totalSteps.setValue(0);
    }

    @Override
    public void step() {
        int totalStepsPrevious = totalStepsOrZero();

        this.totalSteps.postValue(++totalStepsPrevious);

        calculateProgress();
    }

    private void calculateProgress() {
        float progress = (float) totalStepsOrZero() / stepGoal;

        this.progress.postValue(progress);
    }

    @Override
    public MutableLiveData<Float> progress() {
        return progress;
    }

    @Override
    public MutableLiveData<Integer> totalSteps() {
        return totalSteps;
    }

    private int totalStepsOrZero() {
        return this.totalSteps.getValue() == null ? 0 : this.totalSteps.getValue();
    }
}
