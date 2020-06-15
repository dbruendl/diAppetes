package com.example.diappetes.login;

import com.example.diappetes.persistence.model.UserRepository;
import com.example.diappetes.register.InvalidPasswordException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private String loggedInUID;

    @Inject
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Completable login(String uid, String password) {
        return userRepository.findByUid(uid)
                .flatMapCompletable(user -> {
                    if (user.password.equals(password)) {
                        loggedInUID = user.uid;

                        return Completable.complete();
                    }
                    return Completable.error(new InvalidPasswordException());
                });
    }

    @Override
    public Completable logout() {
        return Completable.create(emitter -> {
           loggedInUID = null;

           emitter.onComplete();
        });
    }

    @Override
    public boolean isLoggedIn() {
        return loggedInUID != null;
    }

    @Override
    public String getLoggedInUID() {
        return loggedInUID;
    }
}
