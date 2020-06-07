package com.example.diappetes.login;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "Invalid password";
    }
}
