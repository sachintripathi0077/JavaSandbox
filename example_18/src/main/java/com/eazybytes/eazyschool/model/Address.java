package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "address")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;

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
