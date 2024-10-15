package com.ardwiinoo.loansapi.repository;

import com.ardwiinoo.loansapi.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByLoanId(Long loanId);
}
