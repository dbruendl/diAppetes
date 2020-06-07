package com.example.diappetes;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class RegisterViewModel extends ViewModel {

    private final UserRepository userRepository;

    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Completable registerUser(String email, String username, String password) {
        return userRepository.store(new User(username, email, password));
    }
}
