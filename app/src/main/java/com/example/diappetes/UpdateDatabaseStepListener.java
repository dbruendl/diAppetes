package com.example.diappetes;

import com.example.diappetes.main.StepListener;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserDao;
import com.example.diappetes.persistence.model.UserRepository;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Updates a user's totalSteps field when receiving a step or creates a new Report if none exist
 */
public class UpdateDatabaseStepListener implements StepListener {
    private final String uid;
    private final UserRepository userRepository;

    public UpdateDatabaseStepListener(String uid, UserRepository userRepository) {
        this.uid = uid;
        this.userRepository = userRepository;
    }

    @Override
    public void step() {
        Disposable findUserDisposable = userRepository.findUserReportForTodaySingle(uid)
                .subscribeOn(Schedulers.io())
                .subscribe(report -> {
                    report.steps += 1;

                    userRepository.updateReport(report);
                }, error -> {
                    Report report = new Report(uid);

                    report.steps = ++report.steps;

                    userRepository.insertReport(report);
                });
    }
}
