package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface SettingRepository {
    LiveData<Setting> findById(int id);
    void store(Setting setting);
    List<Setting> findAll();
}
