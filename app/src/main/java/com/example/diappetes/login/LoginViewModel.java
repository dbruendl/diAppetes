package com.example.diappetes.login;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;
import com.example.diappetes.register.InvalidPasswordException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final SavedStateHandle savedStateHandle;

    private final static String LOGGED_IN_USER_ID_KEY = "loggedInUser";

    @ViewModelInject
    public LoginViewModel(
            UserRepository userRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.userRepository = userRepository;
        this.savedStateHandle = savedStateHandle;
    }

    Completable login(String uid, String password) {
        return userRepository.findByUid(uid)
                .flatMapCompletable(user -> {
                    if (user.password.equals(password)) {
                        savedStateHandle.set(LOGGED_IN_USER_ID_KEY, user.uid);

                        return Completable.complete();
                    }
                    return Completable.error(new InvalidPasswordException());
                });
    }

    public String getLoggedInUID() {
        return savedStateHandle.get(LOGGED_IN_USER_ID_KEY);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
