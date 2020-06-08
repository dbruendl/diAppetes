package com.example.diappetes.login;

public class InvalidPasswordException extends ValidationException {

    public InvalidPasswordException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "Invalid password";
    }
}
