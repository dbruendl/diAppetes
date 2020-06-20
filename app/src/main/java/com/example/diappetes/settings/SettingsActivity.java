package com.example.diappetes.settings;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diappetes.databinding.SettingsBinding;
import com.example.diappetes.login.LoginService;
import com.example.diappetes.observer.SettingChangedRepositoryUpdaterObserver;
import com.example.diappetes.observer.StartStepTrackingServiceObserver;
import com.example.diappetes.persistence.model.Setting;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @Inject
    LoginService loginService;

    SettingsViewModel settingsViewModel;

    SettingsBinding binding;
    Disposable loadSettingsDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.settingsToolbar);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding.settingsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loadSettingsDisposable = settingsViewModel.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(SettingsAdapter::new)
                .subscribe(settingsAdapter -> {
                    settingsAdapter.getSettingChecked().observe(this, new SettingChangedRepositoryUpdaterObserver(settingsViewModel.getSettingRepository()));
                    settingsAdapter.getSettingChecked().observe(this, new StartStepTrackingServiceObserver(this, loginService.getLoggedInUID()));
                    binding.settingsRecycler.setAdapter(settingsAdapter);
                }, throwable -> {
                    Log.e(getClass().getSimpleName(), "Error loading settings", throwable);
                });

        binding.settingsToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
