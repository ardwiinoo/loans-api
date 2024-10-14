package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.exception.InvariantError;
import com.ardwiinoo.loansapi.exception.NotFoundError;
import com.ardwiinoo.loansapi.mapper.LoanMapper;
import com.ardwiinoo.loansapi.mapper.UserMapper;
import com.ardwiinoo.loansapi.model.dto.loan.LoanAddRequest;
import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import com.ardwiinoo.loansapi.model.dto.user.UserLoanDto;
import com.ardwiinoo.loansapi.model.entity.Loan;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.model.enums.LoanApproveStatus;
import com.ardwiinoo.loansapi.model.enums.LoanStatus;
import com.ardwiinoo.loansapi.repository.LoanRepository;
import com.ardwiinoo.loansapi.service.LoanService;
import com.ardwiinoo.loansapi.util.UserContextUtil;
import com.ardwiinoo.loansapi.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private UserContextUtil userContextUtil;

    @Override
    public LoanDto addLoan(LoanAddRequest request, MultipartFile[] documents) {
        validationUtil.validate(request);

        User currentUser = userContextUtil.getCurrentUser();

        List<String> docsUrl = new ArrayList<>();
        if (documents != null) {
            for (MultipartFile file : documents) {

                // TODO: konekin ke gcs bang...
                docsUrl.add("gs://dummy-storage/" + file.getOriginalFilename());
            }
        }

        Loan loan = Loan.builder()
                .amount(request.getAmount())
                .interestRate(request.getInterestRate())
                .requestDate(request.getRequestDate())
                .dueDate(request.getDueDate())
                .loanStatus(LoanStatus.PENDING)
                .approveStatus(LoanApproveStatus.PENDING)
                .documents(docsUrl)
                .user(currentUser)
                .build();

        loanRepository.save(loan);

        return LoanMapper.MAPPER.toLoanDtoData(loan);
    }

    @Override
    public LoanDto getLoanById(Long id) {

        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> new NotFoundError("Loan not found")
        );

        return LoanMapper.MAPPER.toLoanDtoData(loan);
    }

    @Override
    public List<LoanDto> getAllLoans() {

        List<Loan> loans = loanRepository.findAll();

        List<LoanDto> loansDto = new ArrayList<>();
        for (Loan loan : loans) {
            loansDto.add(LoanMapper.MAPPER.toLoanDtoData(loan));
        }

        return loansDto;
    }

    @Override
    public LoanDto approveLoan(Long id) {

        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> new NotFoundError("Loan not found")
        );

        if (loan.getApprovedBy() != null) {
           throw new InvariantError("Loan has been approved");
        }

        User currentUser = userContextUtil.getCurrentUser();

        loan.setApproveStatus(LoanApproveStatus.APPROVED);
        loan.setApprovedBy(currentUser);
        loanRepository.save(loan);

        return LoanMapper.MAPPER.toLoanDtoData(loan);
    }
}
