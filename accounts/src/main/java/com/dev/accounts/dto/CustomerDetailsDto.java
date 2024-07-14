package com.dev.accounts.dto;

import com.dev.accounts.annotation.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer Details",
        description = "Schema to hold Cutomer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {
    // from CustomerDto

    @Schema(description = "Name of the customer", example = "John Doe")
    @NotEmpty(message = "Customer name cannot be empty.")
    @Size(min = 3, message = "Customer name must have atleast 3 characters")
    private String name;

    @Schema(description = "Email address of the customer", example = "Johndoe@gmail.com")
    @NotEmpty(message = "Email cannot be blank")
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(description = " 10 digit Mobile Number of the customer", example = "1002003004")
    private String mobileNumber;

    // from AccountDto
    @Schema(description = "Account details of the customer")
    private AccountDto accountDto;

    // from CardsDto
    @Schema(description = "Cards details of the customer")
    private CardsDto cardsDto;

    // from LoansDto
    @Schema(description = "Loans details of the customer")
    private LoanDto loanDto;
}
