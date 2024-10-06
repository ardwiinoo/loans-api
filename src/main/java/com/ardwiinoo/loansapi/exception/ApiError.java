package com.ardwiinoo.loansapi.exception;

import org.springframework.http.HttpStatus;

public class ApiError extends RuntimeException {

    private final HttpStatus statusCode;

    public ApiError(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public ApiError(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
