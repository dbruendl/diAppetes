package com.example.diappetes.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.ActivityInfoBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InfoActivity extends Fragment {
    @Inject
    public InfoViewModel infoViewModel;

    ActivityInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
