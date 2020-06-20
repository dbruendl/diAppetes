package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diappetes.DateUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class UserRepositoryRunnableImpl implements UserRepository {

    private final UserDao userDao;

    @Inject
    public UserRepositoryRunnableImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Completable store(User user) {
        return userDao.insert(user);
    }

    @Override
    public Single<User> findByUid(String uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public LiveData<Report> findUserReportForToday(String uid) {
        Date today = new Date();
        long startOfTodayTimestamp = DateUtils.getStartOfDayMillis(today);
        long endOfTodayTimestamp = DateUtils.getEndOfDayMillis(today);

        return userDao.findUserReportForToday(uid, startOfTodayTimestamp, endOfTodayTimestamp);
    }

    @Override
    public Single<Report> findUserReportForTodaySingle(String uid) {
        return findReportFor(uid, new Date());
    }

    @Override
    public Single<Report> findReportFor(String uid, Date date) {
        long startOfTodayTimestamp = DateUtils.getStartOfDayMillis(date);
        long endOfTodayTimestamp = DateUtils.getEndOfDayMillis(date);

        return userDao.findUserReportForTodaySingle(uid, startOfTodayTimestamp, endOfTodayTimestamp);
    }

    @Override
    public LiveData<UserReports> findUserReports(String uid) {
        return userDao.findUserReports(uid);
    }

    @Override
    public void createReport(String uid, Date created, Integer steps) {
        Report report = new Report(uid);

        report.steps = steps;
        report.created = DateUtils.setStartOfDay(created);

        userDao.insertReport(report);
    }

    @Override
    public void insertReport(Report report) {
        userDao.insertReport(report);
    }

    @Override
    public void updateReport(Report report) {
        userDao.updateReport(report);
    }
}
