package com.example.diappetes.persistence.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SettingRepositoryDaoImpl implements SettingRepository {

    private final SettingDao settingDao;

    @Override
    public void store(Setting setting) {
        Disposable storeDisposable = findById(setting.id)
                .observeOn(Schedulers.io())
                .subscribe(
                        ignored -> settingDao.update(setting).subscribe(),
                        throwable -> settingDao.insert(setting).subscribe());
    }

    private Single<Setting> findById(int id) {
        return settingDao.findById(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public List<Setting> findAll() {
        return settingDao.findAll();
    }
}
