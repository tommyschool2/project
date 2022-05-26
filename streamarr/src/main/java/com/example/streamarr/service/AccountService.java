package com.example.streamarr.service;

import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.dto.AccountDto;

import java.util.UUID;

public interface AccountService {

    Account getAccountById(int id);

    AccountDto createAccount(AccountDto accountDto);
    boolean isValidToken(String token);

    AccountDto connect(String username, String password);
}
