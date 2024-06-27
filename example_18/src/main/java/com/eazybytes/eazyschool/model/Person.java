package com.eazybytes.eazyschool.model;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import com.eazybytes.eazyschool.annotation.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
@Setter
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
    @JsonIgnore
    private String confirmEmail;

    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 5, message = "Password must be of at least 5 characters")
    @PasswordValidator
    @Column(name = "pwd")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Confirm Password field cannot be blank")
    @Size(min = 5, message = "Confirm Password must be of at least 5 characters")
    @Transient
    @JsonIgnore
    private String confirmPassword;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
               joinColumns = {
                        @JoinColumn(name = "person_id", referencedColumnName = "personId")},
                inverseJoinColumns = {
                        @JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    private Set<Courses> courses = new HashSet<>();
}
