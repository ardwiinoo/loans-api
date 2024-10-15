package com.ardwiinoo.loansapi.model.dto.payment;

import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private LoanDto loan;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
