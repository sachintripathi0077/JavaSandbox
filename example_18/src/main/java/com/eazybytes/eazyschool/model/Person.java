package com.eazybytes.eazyschool.model;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import com.eazybytes.eazyschool.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Slf4j
@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email address do not match!"
        ),

        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Password do not match!"
        )
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int personId;

    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 3, message = "Name must be of at least 3 characters")
    private String name;

    @NotBlank(message = "Mobile number filed cannot be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 Digits")
    private String mobileNumber;

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient // should not be stored in the db
    private String confirmEmail;

    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 5, message = "Password must be of at least 5 characters")
    @PasswordValidator
    private String password;

    @NotBlank(message = "Confirm Password field cannot be blank")
    @Size(min = 5, message = "Confirm Password must be of at least 5 characters")
    @Transient
    private String confirmPassword;

}
