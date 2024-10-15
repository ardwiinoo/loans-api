package com.ardwiinoo.loansapi.service;

import com.ardwiinoo.loansapi.model.dto.payment.PaymentAddRequest;
import com.ardwiinoo.loansapi.model.dto.payment.PaymentDto;

public interface PaymentService {
    PaymentDto savePayment(PaymentAddRequest request);
}
