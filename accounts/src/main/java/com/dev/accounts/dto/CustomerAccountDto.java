package com.dev.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerAccountDto {
    // Customer related fields
    @NotEmpty(message = "Customer name cannot be empty.")
    @Size(min = 3, message = "Customer name must have atleast 3 characters")
    private String name;

    @NotEmpty(message = "Email cannot be blank")
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    // Account related fields
    @NotEmpty(message = "Account number must not be blank")
    @Pattern(regexp = "(^|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be blank")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    private String branchAddress;
}
