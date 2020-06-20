package com.example.diappetes.persistence.model;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface SettingDao {
    @Query("SELECT * FROM Setting")
    List<Setting> findAll();

    @Update
    @Transaction
    Completable update(Setting setting);
}
