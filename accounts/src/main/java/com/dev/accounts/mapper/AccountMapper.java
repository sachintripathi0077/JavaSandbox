package com.dev.accounts.mapper;

import com.dev.accounts.dto.AccountDto;
import com.dev.accounts.entity.Account;

public class AccountMapper {

    // below methods will help to map DTO to Entity and vice versa
    public static AccountDto mapToAccountDto(AccountDto accountDto, Account account){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());

        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());

        return account;
    }


}
