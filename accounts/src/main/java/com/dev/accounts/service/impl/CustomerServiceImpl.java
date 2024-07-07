package com.dev.accounts.service.impl;

import com.dev.accounts.dto.CustomerDetailsDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.ICustomerService;
import com.dev.accounts.service.client.CardsFeignClient;
import com.dev.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        // fetching Customer related details
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );

        // fetching Accounts related details
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Accounts","Customer Id",customer.getCustomerId().toString())
        );

       CustomerDetailsDto customerDetailsDto =  CustomerMapper.mapToCustomerDetailsDto(new CustomerDetailsDto(),customer);
    }
}
