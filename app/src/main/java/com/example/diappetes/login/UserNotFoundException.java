package com.example.diappetes.login;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(getFormattedMessage());
    }

    private static String getFormattedMessage() {
        return "User not found";
    }
}
