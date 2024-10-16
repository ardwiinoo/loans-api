package com.ardwiinoo.loansapi.controller;

import com.ardwiinoo.loansapi.model.dto.loan.LoanAddRequest;
import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import com.ardwiinoo.loansapi.service.LoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping(
            value = "/create",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> loanAddHandler(
            @RequestPart("data") LoanAddRequest request,
            @RequestPart("documents") MultipartFile[] documents
    ) {
        LoanDto result = loanService.addLoan(request, documents);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                "data", result
        ));
    }

    @GetMapping("/detail/{loanId}")
    public ResponseEntity<Map<String, Object>> loanDetailHandler(
            @PathVariable("loanId") Long loanId
    ) {
        LoanDto result = loanService.getLoanById(loanId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "success",
                "data", result
        ));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> loanAllHandler() {
        List<LoanDto> result = loanService.getAllLoans();

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "success",
                "data", result
        ));
    }

    @PutMapping("/approve/{loanId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> loanApproveHandler(
            @RequestParam("loanId") Long loanId
    ) {
        LoanDto result = loanService.approveLoan(loanId);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "success",
                "data", result
        ));
    }
}
