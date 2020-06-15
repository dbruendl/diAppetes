package com.example.diappetes.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.diappetes.CreateReportFragment;
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

    private final static int USER_STEP_GOAL = 10;

    @Inject
    public MainViewModel mainViewModel;

    private final int fragmentContainerId;
    private final String loggedInUID;

    private StepTrackingBinding binding;

    CreateReportFragment createReportFragment = new CreateReportFragment();

    public StepTrackingFragment(@IdRes int fragmentContainerId, String loggedInUID) {
        this.fragmentContainerId = fragmentContainerId;
        this.loggedInUID = loggedInUID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StepTrackingBinding.inflate(inflater, container, false);

        LiveData<Report> reportForTodayLiveData = mainViewModel.getUserReportForToday(loggedInUID);

        reportForTodayLiveData.observe(getActivity(), new PetStepGoalObserver(binding.petmini, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new ProgressBarStepGoalObserver(binding.pbSteps, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new TextViewStepObserver(binding.tvSteps));

        binding.trackSteps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent startStepTrackerServiceIntent = new Intent(getActivity(), StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, loggedInUID)
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, MainActivity.CHANNEL_ID);
                getActivity().startService(startStepTrackerServiceIntent);
            } else {
                Intent stopStepTrackerServiceIntent = new Intent(getActivity(), StepTrackerService.class);

                getActivity().stopService(stopStepTrackerServiceIntent);
            }
        });

        binding.createReportButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(fragmentContainerId, createReportFragment)
                    .commit();
        });

        return binding.getRoot();
    }
}
