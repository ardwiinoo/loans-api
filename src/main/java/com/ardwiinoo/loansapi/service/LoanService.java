package com.ardwiinoo.loansapi.service;

import com.ardwiinoo.loansapi.model.dto.loan.LoanAddRequest;
import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import org.springframework.web.multipart.MultipartFile;

public interface LoanService {
    LoanDto addLoan(LoanAddRequest request, MultipartFile[] documents);
}
