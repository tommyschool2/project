package com.example.streamarr.security;

import com.example.streamarr.domain.Account;
import com.example.streamarr.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public UserPrincipalDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws
            UsernameNotFoundException {
        final Account account =
                accountRepository.getAccountByUsername(s);
        if (account == null) throw new UsernameNotFoundException("User login not found");
        return new UserPrincipal(account);
    }
}
