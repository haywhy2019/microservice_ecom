package com.flint.userservice.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super("Invalid email or password");
    }
}
