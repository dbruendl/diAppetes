package com.example.diappetes.login;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import java.util.Optional;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    private Disposable loginUserDisposable;
    private Optional<User> loggedInUserOptional;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    Completable login(String email, String password) {
        CompletableSubject completableSubject = CompletableSubject.create();

        loginUserDisposable = userRepository.findByEmail(email)
                .subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    if(user.password.equals(password)) {
                        loggedInUserOptional = Optional.of(user);
                        completableSubject.onComplete();
                    } else {
                        completableSubject.onError(new InvalidPasswordException());
                    }
                }, error -> {
                    completableSubject.onError(error);
                }, () -> {
                    completableSubject.onError(new UserNotFoundException());
                });

        return completableSubject;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        loginUserDisposable.dispose();
    }
}
