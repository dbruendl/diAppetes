package com.example.diappetes.register;

import android.util.Patterns;

import com.example.diappetes.login.ValidationException;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class AdditionalDataValidator {
    private final String email;
    private final String password;
    private final String uid;
    private final String firstName;
    private final String lastName;
    private final String profession;
    private final String weight;
    private final String height;
    private final String dailyStepGoal;

    public void validate() throws ValidationException {
        validateNotEmptyOrThrow(uid, new InvalidUidException());
        validateNotEmptyOrThrow(firstName, new InvalidFirstNameException());
        validateNotEmptyOrThrow(lastName, new InvalidLastNameException());
        validateNotEmptyOrThrow(profession, new InvalidProfessionException());
        validateNotEmptyOrThrow(weight, new InvalidWeightException());
        validateNotEmptyOrThrow(height, new InvalidHeightException());
    }

    private void validateNotEmptyOrThrow(String string, ValidationException e) {
        if (string.isEmpty()) {
            throw e;
        }
    }
}
