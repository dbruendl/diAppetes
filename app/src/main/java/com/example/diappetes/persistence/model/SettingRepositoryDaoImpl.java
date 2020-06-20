package com.example.diappetes.persistence.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SettingRepositoryDaoImpl implements SettingRepository {

    private final SettingDao settingDao;

    @Override
    public void store(Setting setting) {
        settingDao.update(setting)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public List<Setting> findAll() {
        return settingDao.findAll();
    }
}
