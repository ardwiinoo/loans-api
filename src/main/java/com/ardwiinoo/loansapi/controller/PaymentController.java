package com.ardwiinoo.loansapi.controller;

import com.ardwiinoo.loansapi.model.dto.payment.PaymentAddRequest;
import com.ardwiinoo.loansapi.model.dto.payment.PaymentDto;
import com.ardwiinoo.loansapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/paid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> paymentAddHandler(@RequestBody PaymentAddRequest request) {
        PaymentDto result = paymentService.savePayment(request);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "success",
                "data", result
        ));
    }
}
