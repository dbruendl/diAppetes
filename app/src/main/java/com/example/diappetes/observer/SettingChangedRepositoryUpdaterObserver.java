package com.example.diappetes.observer;

import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Setting;
import com.example.diappetes.persistence.model.SettingRepository;

import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SettingChangedRepositoryUpdaterObserver implements Observer<Setting> {

    private final SettingRepository settingRepository;

    @Override
    public void onChanged(Setting setting) {
        settingRepository.store(setting);
    }
}
