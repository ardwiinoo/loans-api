package com.ardwiinoo.loansapi.advice;

import com.ardwiinoo.loansapi.exception.ApiError;
import com.ardwiinoo.loansapi.exception.InvariantError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomApiErrorExceptionHandler {

    @ExceptionHandler(value = ApiError.class)
    public ResponseEntity<Map<String, Object>> handleCustomApiError(ApiError error) {
        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("status", "fail");
        responseBody.put("message", error.getMessage());

        if (error instanceof InvariantError && ((InvariantError) error).getErrors() != null) {
            responseBody.put("errors", ((InvariantError) error).getErrors());
        }

        return ResponseEntity.status(error.getStatusCode()).body(responseBody);
    }
}
