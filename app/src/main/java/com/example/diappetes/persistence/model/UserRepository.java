package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface UserRepository {
    Completable store(User user);

    Single<User> findByUid(String uid);

    Observable<List<User>> findAll();

    LiveData<Report> findUserReportForToday(String uid);

    Single<Report> findUserReportForTodaySingle(String uid);

    Single<Report> findReportFor(String uid, Date date);

    LiveData<UserReports> findUserReports(String uid);

    void createReport(String uid, Date created, Integer steps);

    void insertReport(Report report);

    void updateReport(Report report);
}
