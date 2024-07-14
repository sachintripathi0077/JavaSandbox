package com.dev.accounts.mapper;

import com.dev.accounts.dto.CustomerDetailsDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Customer;

public class CustomerMapper {

    // below methods will help to map DTO to Entity and vice versa
    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer){
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());

        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());

        return customer;
    }

    public static CustomerDetailsDto mapToCustomerDetailsDto(CustomerDetailsDto customerDetailsDto, Customer customer){
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());

        return customerDetailsDto;
    }

}
