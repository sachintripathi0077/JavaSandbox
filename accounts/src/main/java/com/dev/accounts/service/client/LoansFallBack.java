package com.dev.accounts.service.client;

import com.dev.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBack implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
