package com.ardwiinoo.loansapi.exception;

import java.util.List;

public class InvariantError extends ApiError {

    private List<String> errors;

    public InvariantError(String message) {
        super(message);
    }

    public InvariantError(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
