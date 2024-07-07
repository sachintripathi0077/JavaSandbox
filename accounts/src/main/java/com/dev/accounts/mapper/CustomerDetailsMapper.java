package com.dev.accounts.mapper;

import com.dev.accounts.dto.CustomerDetailsDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;

public class CustomerDetailsMapper {
    public static CustomerDetailsDto mapACToCustomerDetailsDto(Customer customer, Account account,
                                                               CustomerDetailsDto customerDetailsDto){
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());

        customerDetailsDto.
    }
}
