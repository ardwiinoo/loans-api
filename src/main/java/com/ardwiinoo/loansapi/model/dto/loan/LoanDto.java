package com.ardwiinoo.loansapi.model.dto.loan;

import com.ardwiinoo.loansapi.model.dto.user.UserDto;
import com.ardwiinoo.loansapi.model.dto.user.UserLoanDto;
import com.ardwiinoo.loansapi.model.entity.Payment;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.model.enums.LoanApproveStatus;
import com.ardwiinoo.loansapi.model.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoanDto {
    private Long id;
    private UserLoanDto user;
    private UserDto approvedBy;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private LoanApproveStatus approveStatus;
    private LoanStatus loanStatus;
    private LocalDateTime requestDate;
    private LocalDateTime approvedDate;
    private LocalDateTime dueDate;
    private List<Payment> payments;
    private List<String> documents;
}
