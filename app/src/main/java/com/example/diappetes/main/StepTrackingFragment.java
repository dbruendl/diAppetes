package com.example.diappetes.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.diappetes.StepTrackerService;
import com.example.diappetes.databinding.StepTrackingBinding;
import com.example.diappetes.observer.PetStepGoalObserver;
import com.example.diappetes.observer.ProgressBarStepGoalObserver;
import com.example.diappetes.observer.TextViewStepObserver;
import com.example.diappetes.persistence.model.Report;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StepTrackingFragment extends Fragment {

    private final static String LOGGED_IN_USER_ID = "t";
    private final static int USER_STEP_GOAL = 10;

    @Inject
    public MainViewModel mainViewModel;

    private StepTrackingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StepTrackingBinding.inflate(inflater, container, false);

        LiveData<Report> reportForTodayLiveData = mainViewModel.getUserReportForToday(LOGGED_IN_USER_ID);

        reportForTodayLiveData.observe(getActivity(), new PetStepGoalObserver(binding.petmini, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new ProgressBarStepGoalObserver(binding.pbSteps, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new TextViewStepObserver(binding.tvSteps));

        binding.trackSteps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent startStepTrackerServiceIntent = new Intent(getActivity(), StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, LOGGED_IN_USER_ID)
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, MainActivity.CHANNEL_ID);
                getActivity().startService(startStepTrackerServiceIntent);
            } else {
                Intent stopStepTrackerServiceIntent = new Intent(getActivity(), StepTrackerService.class);

                getActivity().stopService(stopStepTrackerServiceIntent);
            }
        });

        return binding.getRoot();
    }
}
