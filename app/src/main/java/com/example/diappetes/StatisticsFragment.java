package com.example.diappetes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.diappetes.databinding.StatisticsBinding;
import com.example.diappetes.observer.UserReportsLineChartObserver;
import com.example.diappetes.persistence.model.UserReports;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatisticsFragment extends Fragment {
    StatisticsBinding binding;

    private final LiveData<UserReports> userReports;

    public StatisticsFragment(LiveData<UserReports> userReports) {
        this.userReports = userReports;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StatisticsBinding.inflate(inflater, container, false);

        userReports.observe(getActivity(), new UserReportsLineChartObserver(binding.weightStepChart));

        return binding.getRoot();
    }
}
