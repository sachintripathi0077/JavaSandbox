package com.dev.accounts.dto;


import com.dev.accounts.annotation.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Schema to hold account details of MicroBank",
        description = "Schema to hold account details of the customers having a account in Microbank "
)
public class AccountDto {

    @NotNull(message = "Account number must not be blank")
    @ValidLong(message = "Account number must be of 10 digits")
    @Schema(description = "Account Number of the customer", example = "Savings")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be blank")
    @Schema(description = "Account Type of the customer", example = "Savings")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    @Schema(description = "Branch Address of the brank office", example = "5th Street, Glemfem, Hinjewadi")
    private String branchAddress;

}
