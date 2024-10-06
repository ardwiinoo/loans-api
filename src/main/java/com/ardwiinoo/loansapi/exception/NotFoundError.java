package com.ardwiinoo.loansapi.exception;

import org.springframework.http.HttpStatus;

public class NotFoundError extends ApiError {

    public NotFoundError(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}