package com.usermanagerservice.UserManagerService.models.exceptions;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}