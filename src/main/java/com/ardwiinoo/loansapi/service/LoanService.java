package com.ardwiinoo.loansapi.service;

import com.ardwiinoo.loansapi.model.dto.loan.LoanAddRequest;
import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanService {
    LoanDto addLoan(LoanAddRequest request);
    LoanDto getLoanById(Long id);
    List<LoanDto> getAllLoans();
    LoanDto approveLoan(Long id);
}
