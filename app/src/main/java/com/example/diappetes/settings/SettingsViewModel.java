package com.example.diappetes.settings;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.Setting;
import com.example.diappetes.persistence.model.SettingRepository;

import java.util.List;

import io.reactivex.Single;
import lombok.Getter;

public class SettingsViewModel extends ViewModel {
    @Getter
    private final SettingRepository settingRepository;

    @ViewModelInject
    public SettingsViewModel(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Single<List<Setting>> findAll() {
        return Single.create(emitter -> {
            List<Setting> settings = settingRepository.findAll();

            emitter.onSuccess(settings);
        });
    }

    public LiveData<Setting> findById(int id) {
        return settingRepository.findById(id);
    }
}
