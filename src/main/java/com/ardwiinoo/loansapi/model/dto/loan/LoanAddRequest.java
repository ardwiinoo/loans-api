package com.ardwiinoo.loansapi.model.dto.loan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanAddRequest {

    @NotNull
    @Min(100000)
    private BigDecimal amount;

    @NotNull
    private BigDecimal interestRate;

    @NotNull
    private LocalDateTime requestDate;

    @NotNull
    private LocalDateTime dueDate;

    private MultipartFile[] documents;
}