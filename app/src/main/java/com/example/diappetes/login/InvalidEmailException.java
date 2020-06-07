package com.example.diappetes.login;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
    }

    private static String getFormattedMessage() {
        return "Invalid email";
    }
}
