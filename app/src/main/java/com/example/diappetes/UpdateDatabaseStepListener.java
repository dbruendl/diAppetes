package com.example.diappetes;

import com.example.diappetes.main.StepListener;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserDao;

import io.reactivex.disposables.Disposable;

/**
 * Updates a user's totalSteps field when receiving a step or creates a new Report if none exist
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
                    Report reportForToday = userReports.getReportForToday();

                    if(reportForToday == null) {
                        Report report = new Report(uid);

                        report.steps = ++report.steps;

                        return userDao.insertReport(report);
                    }

                    reportForToday.steps = ++reportForToday.steps;

                    return userDao.updateReport(reportForToday);
                })
                .subscribe();
    }
}
