package com.example.streamarr.controller;

import com.example.streamarr.domain.dto.AccountDto;
import com.example.streamarr.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AccountDto> connect(@RequestBody AccountDto accountDto) {
        AccountDto accDto = accountService.connect(accountDto.getUsername(), accountDto.getPassword());
        if (accDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(accDto, HttpStatus.OK);
    }

}
