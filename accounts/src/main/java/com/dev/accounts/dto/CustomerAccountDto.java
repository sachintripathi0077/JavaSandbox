package com.dev.accounts.dto;

import com.dev.accounts.annotation.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Schema to hold Customer-Account details",
        description = "Schema to map customer-account details for MicroBank Accounts"
)
public class CustomerAccountDto {
    // Customer related fields
    @NotEmpty(message = "Customer name cannot be empty.")
    @Size(min = 3, message = "Customer name must have atleast 3 characters")
    @Schema(description = "Name of the customer", example = "John Doe")
    private String name;

    @NotEmpty(message = "Email cannot be blank")
    @Email
    @Schema(description = "Email address of the customer", example = "Johndoe@gmail.com")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(description = " 10 digit Mobile Number of the customer", example = "1002003004")
    private String mobileNumber;

    // Account related fields
    @NotNull(message = "Account number must not be blank")
    @ValidLong(message = "Account number must be of 10 digits")
    @Schema(description = "Account Number of the customer", example = "1002003004")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be blank")
    @Schema(description = "Account Type of the customer", example = "Savings")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    @Schema(description = "Branch Address of the brank office", example = "5th Street, Glemfem, Hinjewadi")
    private String branchAddress;
}
