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

    // from CardsDto

    @NotEmpty(message = "Card Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits")
    @Schema(
            description = "Card Number of the customer", example = "100646930341"
    )
    private String cardNumber;

    @NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
            description = "Type of the card", example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total amount limit available against a card", example = "100000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "90000"
    )
    private int availableAmount;


    // from LoansDto

    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    private String loanType;

    @Positive(message = "Total loan amount must be greater than zero.")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid must be grater than equal to zero.")
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    private int outstandingAmount;
}
