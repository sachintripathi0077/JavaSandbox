package com.dev.loans.controller;

import com.dev.loans.service.LoanService;
import com.dev.loans.constants.LoanConstants;
import com.dev.loans.dto.LoanDto;
import com.dev.loans.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
public class LoanController {

    private LoanService loanService;

    @GetMapping(value = "/helloWorld")
    public String sayHello(){
        return "Hello World!";
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
                                                  @RequestParam(name = "mobileNumber") String mobileNumber){
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201,LoanConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> fetchLoanDetails(@Valid
                                                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
                                                    @RequestParam(name = "mobileNumber") String mobileNumber){
        LoanDto loanDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid
                                                         @RequestBody LoanDto loanDto){
        boolean isUpdated = loanService.updateLoan(loanDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200,LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@Valid
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
                                                  @RequestParam(name = "mobileNumber") String mobileNumber){
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200,LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_DELETE));
        }
    }
}
