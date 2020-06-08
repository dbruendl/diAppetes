package com.example.diappetes.login;

public class InvalidUidException extends ValidationException {
    public InvalidUidException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "Invalid username";
    }
}
