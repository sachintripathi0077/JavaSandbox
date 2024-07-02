package com.dev.loans.mapper;

import com.dev.loans.dto.LoanDto;
import com.dev.loans.entity.Loan;

public class LoanMapper {

    public static LoanDto mapToLoanDto(LoanDto loanDto, Loan loan){
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());

        return loanDto;
    }

    public static Loan mapToLoan(LoanDto loanDto, Loan loan) {
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());

        return loan;
    }

}
