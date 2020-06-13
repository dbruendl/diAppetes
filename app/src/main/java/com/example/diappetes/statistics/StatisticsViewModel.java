package com.example.diappetes.statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.UserReports;
import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

public class StatisticsViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public StatisticsViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserReports> findAllReports(String uid) {
        return userRepository.findUserReports(uid);
    }
}
