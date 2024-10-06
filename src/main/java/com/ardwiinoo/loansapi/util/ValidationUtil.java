package com.ardwiinoo.loansapi.util;

import com.ardwiinoo.loansapi.exception.InvariantError;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ValidationUtil {

    private Validator validator;

    public void validate(Object any) {
        var result = validator.validate(any);

        if (!result.isEmpty()) {
            List<String> errors = result.stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .toList();

            throw new InvariantError("Validation failed", errors);
        }
    }
}
