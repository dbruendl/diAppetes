package com.example.diappetes.login;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "Invalid email";
    }
}
