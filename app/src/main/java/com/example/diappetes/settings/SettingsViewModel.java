package com.example.diappetes.settings;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.Setting;
import com.example.diappetes.persistence.model.SettingRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SettingsViewModel extends ViewModel {
    @Getter
    private final SettingRepository settingRepository;

    public Single<List<Setting>> findAll() {
        return Single.create(emitter -> {
            List<Setting> settings = settingRepository.findAll();

            emitter.onSuccess(settings);
        });
    }
}
