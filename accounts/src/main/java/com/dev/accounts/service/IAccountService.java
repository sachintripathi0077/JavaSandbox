package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerAccountDto;
import com.dev.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    public void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - String Object
     */
    public CustomerAccountDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerAccountDto customerAccountDto);

    boolean deleteAccount(String mobileNumber);

    boolean updateCommunicationStatus(Long accountNumber);
}
