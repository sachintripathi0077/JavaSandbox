package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    public void createAccount(CustomerDto customerDto);
}
