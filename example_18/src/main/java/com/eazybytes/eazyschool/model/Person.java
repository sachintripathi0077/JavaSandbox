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
@Table(name = "person")
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "person_id")
    private int personId;

    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 3, message = "Name must be of at least 3 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Mobile number filed cannot be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 Digits")
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient // should not be stored in the db
    private String confirmEmail;

    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 5, message = "Password must be of at least 5 characters")
    @PasswordValidator
    @Column(name = "pwd")
    private String password;

    @NotBlank(message = "Confirm Password field cannot be blank")
    @Size(min = 5, message = "Confirm Password must be of at least 5 characters")
    @Transient
    private String confirmPassword;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;
}
