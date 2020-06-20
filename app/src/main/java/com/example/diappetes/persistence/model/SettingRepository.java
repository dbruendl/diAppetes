package com.example.diappetes.persistence.model;

import java.util.List;

public interface SettingRepository {
    void store(Setting setting);
    List<Setting> findAll();
}
