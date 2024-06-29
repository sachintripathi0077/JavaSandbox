package com.dev.accounts.service.impl;

import com.dev.accounts.constants.AccountConstants;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.CustomerAlreadyExistException;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    // Autowiring Repositories
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());

        // checking if this passed customer already exists using mobile number
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(customerOptional.isPresent()){
            throw new CustomerAlreadyExistException("An customer is already registered with given mobile number." +
                    customerDto.getMobileNumber());
        }
        customer.setCreated_at(LocalDateTime.now());
        customer.setCreated_by("Me");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer){
        // creating bank account with the given customer details
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        account.setBranchAddress(AccountConstants.ADDRESS);
        account.setAccountNumber(100000000L + new Random().nextInt(900000000));
        account.setAccountType(AccountConstants.SAVINGS);

        return account;
    }
}
