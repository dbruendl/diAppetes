package com.example.diappetes.register;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

import static io.reactivex.Completable.concat;

public class RegisterViewModel extends ViewModel {

    private final UserRepository userRepository;

    private Disposable usernameAlreadyTakenDisposable;

    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Completable registerUser(String uid,
                             String email,
                             String password,
                             String passwordConfirmation,
                             String firstName,
                             String lastName,
                             String profession,
                             Double weight,
                             Double height,
                             int dailyStepGoal) {
        return userRepository.store(new User(uid, email, password, firstName, lastName, profession, weight, height, dailyStepGoal));
    }

    Completable usernameAlreadyTaken(String uid) {
        CompletableSubject completableSubject = CompletableSubject.create();

        usernameAlreadyTakenDisposable = userRepository.findByUid(uid)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        user -> completableSubject.onError(new UsernameAlreadyTakenException()),
                        error -> completableSubject.onComplete());

        return completableSubject;
    }

    private void validateEmailOrThrow(String email) {
        if(email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    private void validateUidOrThrow(String uid) {
        if(uid.isEmpty()) {
            throw new InvalidUidException();
        }
    }


    private void validatePasswordOrThrow(String password, String passwordConfirmation) {
        if(password.isEmpty() || passwordConfirmation.isEmpty()) {
            throw new InvalidPasswordException();
        } else if(!password.equals(passwordConfirmation)) {
            throw new PasswordDoNotMatchException();
        }
    }
}
