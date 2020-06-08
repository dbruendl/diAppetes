package com.example.diappetes.login;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    private Disposable loginUserDisposable;
    private User loggedInUser;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Completable login(String uid, String password) {
        return userRepository.findByUid(uid)
                .flatMapCompletable(user -> {
                    if (user.password.equals(password)) {
                        loggedInUser = user;

                        return Completable.complete();
                    }
                    return Completable.error(new InvalidPasswordException());
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        loginUserDisposable.dispose();
    }
}
