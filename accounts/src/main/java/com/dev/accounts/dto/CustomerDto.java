package com.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Cutomer details"
)
public class CustomerDto {
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
}
