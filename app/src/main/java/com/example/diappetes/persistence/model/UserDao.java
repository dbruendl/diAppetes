package com.example.diappetes.persistence.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Observable<List<User>> getAll();

    @Insert
    Completable insert(User user);

    @Query("SELECT * FROM user WHERE uid = :uid")
    Single<User> findByUid(String uid);

    @Transaction
    @Query("SELECT * FROM user WHERE uid = :uid")
    Observable<UserReports> findUserReportsByUid(String uid);

    @Update
    @Transaction
    Completable updateReport(Report report);

    @Insert
    Completable insertReport(Report report);
}
