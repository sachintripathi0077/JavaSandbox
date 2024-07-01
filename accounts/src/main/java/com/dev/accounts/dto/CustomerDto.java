package com.dev.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Customer name cannot be empty.")
    @Size(min = 3, message = "Customer name must have atleast 3 characters")
    private String name;

    @NotEmpty(message = "Email cannot be blank")
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
