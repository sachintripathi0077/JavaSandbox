package com.eazybytes.eazyschool.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long ")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Mobile number must not be black")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digits")
    @Column(name = "mobile_num")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Address Line 1 field cannot be blank")
    @Column(name = "address1")
    @Size(min = 5, message = "Address Line 1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message = "City field cannot be blank")
    @Size(min = 3, message = "City name must be at least 3 characters long")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "State field cannot be blank")
    @Size(min = 3, message = "State name must be at least 3 characters long")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Zip Code field cannot be blank")
    @Column(name = "zip_code")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Zip Code must be at least 6 digits")
    private String zipCode;
}
