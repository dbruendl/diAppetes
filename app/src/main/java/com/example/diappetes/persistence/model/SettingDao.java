package com.example.diappetes.persistence.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface SettingDao {
    @Query("SELECT * FROM Setting")
    List<Setting> findAll();

    @Update
    @Transaction
    Completable update(Setting setting);

    @Query("SELECT * FROM Setting WHERE id = :id")
    Single<Setting> findById(int id);

    @Insert
    @Transaction
    Completable insert(Setting setting);
}
