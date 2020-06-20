package com.example.diappetes.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserReports;
import com.example.diappetes.persistence.model.UserRepository;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    public LiveData<Report> getUserReportForToday(String uid) {
        return userRepository.findUserReportForToday(uid);
    }

    public LiveData<UserReports> findAllReports(String uid) {
        return userRepository.findUserReports(uid);
    }
}
