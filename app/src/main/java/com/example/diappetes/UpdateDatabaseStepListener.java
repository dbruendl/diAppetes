package com.example.diappetes;

import com.example.diappetes.main.StepListener;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserDao;

import io.reactivex.disposables.Disposable;

/**
 * Updates a user's totalSteps field when receiving a step
 */
public class UpdateDatabaseStepListener implements StepListener {
    private final String uid;
    private final UserDao userDao;

    public UpdateDatabaseStepListener(String uid, UserDao userDao) {
        this.uid = uid;
        this.userDao = userDao;
    }

    @Override
    public void step() {
        Disposable findUserDisposable = userDao.findUserReportsByUid(uid)
                .flatMapCompletable(userReports -> {
                    Report report = userReports.getReportForToday();

                    report.steps = ++report.steps;

                    return userDao.updateReport(report);
                })
                .subscribe();
    }
}
