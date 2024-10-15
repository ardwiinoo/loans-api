package com.ardwiinoo.loansapi.repository;

import com.ardwiinoo.loansapi.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByUserId(long userId);
}
