package com.ardwiinoo.loansapi.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationError extends ApiError {

    public AuthenticationError(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
