package com.example.diappetes.register;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.login.InvalidEmailException;
import com.example.diappetes.login.InvalidPasswordException;
import com.example.diappetes.login.InvalidUidException;
import com.example.diappetes.login.PasswordDoNotMatchException;
import com.example.diappetes.login.ValidationException;
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

    Completable registerUser(String email, String uid, String password, String passwordConfirmation) {
        try {
            validateEmailOrThrow(email);
            validateUidOrThrow(uid);
            validatePasswordOrThrow(password, passwordConfirmation);
        } catch (ValidationException e) {
            return Completable.error(e);
        }

        return userRepository.store(new User(uid, email, password));
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
