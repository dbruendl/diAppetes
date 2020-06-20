package com.example.diappetes.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diappetes.StepTrackerService;
import com.example.diappetes.databinding.SettingsBinding;
import com.example.diappetes.login.LoginService;
import com.example.diappetes.main.MainActivity;

import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    @Inject
    LoginService loginService;

    SettingsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.settingsToolbar);

        binding.settingsToolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        binding.enableStepTracking.setOnClickListener(v -> {
            binding.checkboxStepTracking.setChecked(!binding.checkboxStepTracking.isChecked());
        });

        binding.checkboxStepTracking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent startStepTrackerServiceIntent = new Intent(this, StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, loginService.getLoggedInUID())
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, MainActivity.CHANNEL_ID);
                startService(startStepTrackerServiceIntent);
            } else {
                stopService(new Intent(this, StepTrackerService.class));
            }
        });
    }
}
