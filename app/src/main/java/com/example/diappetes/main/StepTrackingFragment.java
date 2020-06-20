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

public class StepTrackingFragment extends Fragment {

    private final static int USER_STEP_GOAL = 10;

    private final int fragmentContainerId;
    private final String loggedInUID;
    private final LiveData<Report> reportForTodayLiveData;

    private StepTrackingBinding binding;

    public StepTrackingFragment(@IdRes int fragmentContainerId, String loggedInUID, LiveData<Report> reportForTodayLiveData) {
        this.fragmentContainerId = fragmentContainerId;
        this.loggedInUID = loggedInUID;
        this.reportForTodayLiveData = reportForTodayLiveData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StepTrackingBinding.inflate(inflater, container, false);

        reportForTodayLiveData.observe(getActivity(), new PetStepGoalObserver(binding.petmini, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new ProgressBarStepGoalObserver(binding.pbSteps, USER_STEP_GOAL));
        reportForTodayLiveData.observe(getActivity(), new TextViewStepObserver(binding.tvSteps));

        binding.createReportButton.setOnClickListener(v -> {
            CreateReportFragment createReportFragment = new CreateReportFragment(loggedInUID);

            getParentFragmentManager().beginTransaction()
                    .replace(fragmentContainerId, createReportFragment)
                    .commit();
        });

        return binding.getRoot();
    }
}
