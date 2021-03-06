package com.example.diappetes.register;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AdditionalDataViewModel extends ViewModel {
    private final UserRepository userRepository;

    Completable register(String uid, String email, String password, String firstName, String lastName,
                         String profession, Double weight, Double height, int dailyStepGoal) {
        User user = new User(uid, email, password, firstName, lastName, profession, weight, height, dailyStepGoal);

        return userRepository.store(user);
    }
}
