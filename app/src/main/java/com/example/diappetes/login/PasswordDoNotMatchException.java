package com.example.diappetes.login;

public class PasswordDoNotMatchException extends ValidationException {
    public PasswordDoNotMatchException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "Passwords do not match";
    }

}
