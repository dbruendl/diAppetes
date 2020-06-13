package com.example.diappetes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.StatisticsBinding;
import com.example.diappetes.main.MainActivity;
import com.example.diappetes.observer.UserReportsLineChartObserver;
import com.example.diappetes.statistics.StatisticsViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatisticsFragment extends Fragment {
    StatisticsBinding binding;

    @Inject
    public StatisticsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StatisticsBinding.inflate(inflater, container, false);

        viewModel.findAllReports(MainActivity.LOGGED_IN_USER_ID).observe(getActivity(), new UserReportsLineChartObserver(binding.weightStepChart));

        return binding.getRoot();
    }
}
