package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM Setting WHERE id = :id")
    LiveData<Setting> findById(int id);

    @Update
    @Transaction
    Completable update(Setting setting);
}
