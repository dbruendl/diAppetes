package com.example.diappetes.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diappetes.StepTrackerService;
import com.example.diappetes.databinding.SettingsBinding;
import com.example.diappetes.login.LoginService;
import com.example.diappetes.main.MainActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @Inject
    LoginService loginService;

    SettingsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.settingsToolbar);

        binding.settingsRecycler.setLayoutManager(new LinearLayoutManager(this));

        List<Setting> settings = Collections.singletonList(new Setting(1, "Enable step tracking"));

        SettingsAdapter settingsAdapter = new SettingsAdapter(settings);

        settingsAdapter.getSettingChecked().observe(this, setting -> {
            if(setting.isEnabled()) {
                Intent startStepTrackerServiceIntent = new Intent(this, StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, loginService.getLoggedInUID())
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, MainActivity.CHANNEL_ID);

                startService(startStepTrackerServiceIntent);
            } else {
                stopService(new Intent(this, StepTrackerService.class));
            }
        });
        binding.settingsRecycler.setAdapter(settingsAdapter);

        binding.settingsToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
