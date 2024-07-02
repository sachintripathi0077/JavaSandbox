package com.dev.loans.service;

import com.dev.loans.constants.LoanConstants;
import com.dev.loans.dto.LoanDto;
import com.dev.loans.entity.Loan;
import com.dev.loans.exception.LoanAlreadyExistsException;
import com.dev.loans.exception.ResourceNotFoundException;
import com.dev.loans.mapper.LoanMapper;
import com.dev.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanService {

    private LoanRepository loanRepository;

    public void createLoan(String mobileNumber) {
        // checking if any similar loan already exists with the provided mobile number
        // and throwing exception if it is already present for the given account or else
        // creating a new load and saving it back to the db
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered for mobile number: " + mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));

    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber)
        );
        return LoanMapper.mapToLoanDto(new LoanDto(), loan);
    }

    public boolean updateLoan(LoanDto loansDto) {
        Loan loan = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoan(loansDto, loan);
        loanRepository.save(loan);
        return  true;
    }

    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }
}
