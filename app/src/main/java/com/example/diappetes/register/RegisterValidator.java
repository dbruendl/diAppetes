package com.example.diappetes.register;

import android.util.Patterns;

import com.example.diappetes.login.ValidationException;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RegisterValidator {

    private final String email;
    private final String uid;
    private final String password;
    private final String passwordConfirmation;

    public void validate() throws ValidationException {
        validateEmailOrThrow(email);
        validateNotEmptyOrThrow(uid, new InvalidUidException());
        validateNotEmptyOrThrow(password, new InvalidPasswordException());
        validateNotEmptyOrThrow(passwordConfirmation, new InvalidPasswordException());
        validatePasswordsMatchOrThrow(password, passwordConfirmation);
    }

    private void validateEmailOrThrow(String email) {
        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    private void validateNotEmptyOrThrow(String string, ValidationException e) {
        if (string.isEmpty()) {
            throw e;
        }
    }

    private void validatePasswordsMatchOrThrow(String password, String passwordConfirmation) {
        if(!password.equals(passwordConfirmation)) {
            throw new PasswordDoNotMatchException();
        }
    }
}
