package com.ardwiinoo.loansapi.model.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentAddRequest {

    @NotNull
    private BigDecimal amount;
}
