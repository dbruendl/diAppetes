package com.example.diappetes.tracker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserRepository;

public class StepGoalProgressTrackerObserverImpl implements Observer<Report>, StepGoalTracker {
    private final MutableLiveData<Float> progressLiveData;
    private final int stepGoal;

    public StepGoalProgressTrackerObserverImpl(int stepGoal) {
        progressLiveData = new MutableLiveData<>();
        this.stepGoal = stepGoal;
    }

    @Override
    public void onChanged(Report report) {
        float progress = (float) report.steps / stepGoal;

        progressLiveData.postValue(progress);
    }

    private void observeUserReport(UserRepository userRepository, String uid) {
        userRepository.findUserReportForToday(uid).observeForever(report -> {

        });
    }

    @Override
    public MutableLiveData<Float> progress() {
        return progressLiveData;
    }
}
