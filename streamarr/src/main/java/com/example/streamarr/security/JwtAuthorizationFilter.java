package com.example.streamarr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.streamarr.domain.Account;
import com.example.streamarr.repository.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final AccountRepository accountRepository;
    public JwtAuthorizationFilter(AuthenticationManager
                                          authenticationManager, AccountRepository accountRepository) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null ||
                !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication =
                getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
    private Authentication
    getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        if (token != null) {
            String username = JWT
                    .require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JwtProperties.TOKEN_PREFIX,
                            ""))
                    .getSubject();
            final Account account =
                    accountRepository.getAccountByUsername(username);
            if (account == null) throw new IllegalArgumentException("User login not found " + username);
            UserPrincipal principal = new UserPrincipal(account);
            return new UsernamePasswordAuthenticationToken(principal,
                    null, principal.getAuthorities());
        }
        return null;
    }
}