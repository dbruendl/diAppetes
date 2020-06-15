package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
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
    Single<UserReports> findUserReportsByUid(String uid);

    @Transaction
    @Query("SELECT * FROM user WHERE uid = :uid")
    LiveData<UserReports> findUserReports(String uid);

    @Update
    @Transaction
    void updateReport(Report report);

    @Insert
    void insertReport(Report report);

    @Query("SELECT * FROM report WHERE userId = :uid AND created BETWEEN :startOfTodayTimestamp AND :endOfTodayTimestamp")
    LiveData<Report> findUserReportForToday(String uid, long startOfTodayTimestamp, long endOfTodayTimestamp);

    @Query("SELECT * FROM report WHERE userId = :uid AND created BETWEEN :startOfTodayTimestamp AND :endOfTodayTimestamp")
    Single<Report> findUserReportForTodaySingle(String uid, long startOfTodayTimestamp, long endOfTodayTimestamp);
}
