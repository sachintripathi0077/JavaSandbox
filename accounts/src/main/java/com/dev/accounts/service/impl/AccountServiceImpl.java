package com.dev.accounts.service.impl;

import com.dev.accounts.constants.AccountConstants;
import com.dev.accounts.dto.AccountDto;
import com.dev.accounts.dto.AccountsMsgDto;
import com.dev.accounts.dto.CustomerAccountDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.entity.Account;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.CustomerAlreadyExistException;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.CustomerAcoountMapper;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(IAccountService.class);

    // Autowiring Repositories
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private final StreamBridge streamBridge;

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

        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccount = accountRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);
    }

    private void sendCommunication(Account account, Customer customer){
        var accountMsgDto = new AccountsMsgDto(account.getAccountNumber(),customer.getName(),
                customer.getEmail(),customer.getMobileNumber());
        logger.info("Sending communication request for details: {}", accountMsgDto);
        var result = streamBridge.send("sendCommunication-out-0",accountMsgDto);
        logger.info("Communication request status: {}", result);
    }

    /**
     * @param mobileNumber - String Object
     */
    @Override
    public CustomerAccountDto fetchAccount(String mobileNumber) {
        // fetch logic for customer
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);
        if(!customerOptional.isPresent()){
            throw new ResourceNotFoundException("Customer","mobileNumber",mobileNumber);
        }

        // fetch logic for account
        Optional<Account> accountOptional= accountRepository.findByCustomerId(customerOptional.get().getCustomerId());
        if(!accountOptional.isPresent()){
            throw new ResourceNotFoundException("Account","customerId",customerOptional.get().getCustomerId().toString());
        }

        // mapping the fetched data to CustomerAccountDto
        CustomerAccountDto customerAccountDto = CustomerAcoountMapper.mapToCustomerAccountDto(customerOptional.get(),
                accountOptional.get(), new CustomerAccountDto());

        return customerAccountDto;
    }

    @Override
    public boolean updateAccount(CustomerAccountDto customerAccountDto) {
        // Getting account details based on the customerDto
        AccountDto accountDto = CustomerAcoountMapper.mapToAccountDto(customerAccountDto);
        Optional<Account> accountOptional = accountRepository.findById(accountDto.getAccountNumber());
        if(!accountOptional.isPresent()){
            throw new ResourceNotFoundException("Account","Account Number",accountDto.getAccountNumber().toString());
        }

        // updating the account/customer details using the fetched account information from db
        accountOptional.get().setAccountType(customerAccountDto.getAccountType());
        accountOptional.get().setBranchAddress(customerAccountDto.getBranchAddress());
        accountOptional.get().setUpdated_at(LocalDateTime.now());
        accountOptional.get().setUpdated_by("Again Me");
        accountRepository.save(accountOptional.get());

        // fetching customer details for update
        Optional<Customer> customerOptional = customerRepository.findById(accountOptional.get().getCustomerId());
        if(!customerOptional.isPresent()){
            throw new ResourceNotFoundException("Customer","Customer Id", accountOptional.get().getCustomerId().toString());
        }
        customerOptional.get().setName(customerAccountDto.getName());
        customerOptional.get().setEmail(customerAccountDto.getEmail());
        customerOptional.get().setMobileNumber(customerAccountDto.getMobileNumber());
        customerOptional.get().setUpdated_at(LocalDateTime.now());
        customerOptional.get().setUpdated_by("Again Me");
        customerRepository.save(customerOptional.get());
        return true;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile Number", mobileNumber)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Account createNewAccount(Customer customer){
        // creating bank account with the given customer details
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        account.setBranchAddress(AccountConstants.ADDRESS);
        account.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        account.setAccountType(AccountConstants.SAVINGS);

        return account;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber!=null){
            Account account = accountRepository.findById(accountNumber).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","AccountNumber",accountNumber.toString())
            );
            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return isUpdated;
    }
}
