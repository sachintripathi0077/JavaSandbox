package com.dev.accounts.service;

import com.dev.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
