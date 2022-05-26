package com.example.streamarr.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.dto.AccountDto;
import com.example.streamarr.repository.AccountRepository;
import com.example.streamarr.security.JwtProperties;
import com.example.streamarr.service.AccountService;
import com.example.streamarr.service.ChatService;
import com.example.streamarr.service.mapper.AccountMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ChatService chatService;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository, ChatService chatService) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.chatService = chatService;
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.getById(id);
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);

        String token = JWT.create()
                .withSubject(account.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +
                JwtProperties.EXPIRATION_TIME)).sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        account.setToken(token);
        account = accountRepository.save(account);

        // Create chat
        chatService.createChat(account);

        return accountMapper.toDto(account);
    }

    @Override
    public boolean isValidToken(String token) {
        return accountRepository.getAccountByToken(token) != null;
    }

    @Override
    public AccountDto connect(String username, String password) {
        Account account = accountRepository.getAccountByUsername(username);
        if (!new BCryptPasswordEncoder().matches(password, account.getPassword())) {
            return null; // Not right password
        }

        return accountMapper.toDto(account);
    }
}
