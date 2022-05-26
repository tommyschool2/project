package com.example.streamarr.repository;

import com.example.streamarr.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountByToken(String token);
    Account getAccountByUsername(String username);
}