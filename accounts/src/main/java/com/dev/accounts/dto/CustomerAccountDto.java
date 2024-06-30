package com.dev.accounts.dto;

import lombok.Data;

@Data
public class CustomerAccountDto {
    // Customer related fields
    private String name;

    private String email;

    private String mobileNumber;

    // Account related fields
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
