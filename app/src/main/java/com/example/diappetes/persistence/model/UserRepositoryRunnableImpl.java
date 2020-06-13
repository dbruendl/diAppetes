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
    public Observable<List<User>> findAll() {
        return userDao.getAll();
    }

    @Override
    public LiveData<Report> findUserReportForToday(String uid) {
        Date today = new Date();
        long startOfTodayTimestamp = DateUtils.getStartOfDayMillis(today);
        long endOfTodayTimestamp = DateUtils.getEndOfDayMillis(today);

        return userDao.findUserReportForToday(uid, startOfTodayTimestamp, endOfTodayTimestamp);
    }

    @Override
    public LiveData<UserReports> findUserReports(String uid) {
        return userDao.findUserReports(uid);
    }

    @Override
    public Completable updateReport(Report report) {
        return userDao.insertReport(report);
    }
}
