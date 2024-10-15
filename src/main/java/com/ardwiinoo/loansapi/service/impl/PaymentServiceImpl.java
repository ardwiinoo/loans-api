package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.exception.InvariantError;
import com.ardwiinoo.loansapi.exception.NotFoundError;
import com.ardwiinoo.loansapi.mapper.PaymentMapper;
import com.ardwiinoo.loansapi.model.dto.payment.PaymentAddRequest;
import com.ardwiinoo.loansapi.model.dto.payment.PaymentDto;
import com.ardwiinoo.loansapi.model.entity.Loan;
import com.ardwiinoo.loansapi.model.entity.Payment;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.repository.LoanRepository;
import com.ardwiinoo.loansapi.repository.PaymentRepository;
import com.ardwiinoo.loansapi.service.PaymentService;
import com.ardwiinoo.loansapi.util.UserContextUtil;
import com.ardwiinoo.loansapi.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserContextUtil userContextUtil;

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    public PaymentDto savePayment(PaymentAddRequest request) {
        validationUtil.validate(request);

        User user = userContextUtil.getCurrentUser();
        Loan loan = loanRepository.findByUserId(user.getId()).orElseThrow(
                () -> new NotFoundError("Loan not found")
        );

        BigDecimal amount = null;
        if (loan.getPayments() != null) {
            for (Payment payment : loan.getPayments()) {
                if (payment.getAmount() != null) {
                    amount.add(payment.getAmount());
                }
            }
        }

        if (amount != null && amount.equals(loan.getAmount())) {
            throw new InvariantError("Loan is already paid off");
        }

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .loan(loan)
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return PaymentMapper.MAPPER.toPaymentDto(payment);
    }
}
