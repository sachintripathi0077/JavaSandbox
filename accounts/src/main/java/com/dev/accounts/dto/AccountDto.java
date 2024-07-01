package com.dev.accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {

    @NotEmpty(message = "Account number must not be blank")
    @Pattern(regexp = "(^|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be blank")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    private String branchAddress;

}
