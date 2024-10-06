package com.ardwiinoo.loansapi.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationError extends ApiError {

    public AuthorizationError(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}