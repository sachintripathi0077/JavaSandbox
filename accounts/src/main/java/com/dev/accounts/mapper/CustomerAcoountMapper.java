package com.dev.accounts.mapper;

import com.dev.accounts.dto.AccountDto;
import com.dev.accounts.dto.CustomerAccountDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;

public class CustomerAcoountMapper {

    public static CustomerAccountDto mapToCustomerAccountDto(Customer customer, Account account, CustomerAccountDto customerAccountDto){

        // copying customer properties into the dto object
        customerAccountDto.setName(customer.getName());
        customerAccountDto.setEmail(customer.getEmail());
        customerAccountDto.setMobileNumber(customer.getMobileNumber());

        // copying account properties into the dto
        customerAccountDto.setAccountNumber(account.getAccountNumber());
        customerAccountDto.setAccountType(account.getAccountType());
        customerAccountDto.setBranchAddress(account.getBranchAddress());

        return customerAccountDto;
    }

    public static CustomerDto mapToCustomerDto(CustomerAccountDto customerAccountDto) {
        CustomerDto customerDto = new CustomerDto();

        // Mapping customer details
        customerDto.setName(customerAccountDto.getName());
        customerDto.setEmail(customerAccountDto.getEmail());
        customerDto.setMobileNumber(customerAccountDto.getMobileNumber());

        return customerDto;
    }

    public static AccountDto mapToAccountDto(CustomerAccountDto customerAccountDto) {
        AccountDto accountDto = new AccountDto();

        // Mapping account details
        accountDto.setAccountNumber(customerAccountDto.getAccountNumber());
        accountDto.setAccountType(customerAccountDto.getAccountType());
        accountDto.setBranchAddress(customerAccountDto.getBranchAddress());

        return accountDto;
    }
}

