package com.example.streamarr.security;

import com.example.streamarr.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserPrincipalDetailsService
            userPrincipalDetailsService;
    private final AccountRepository accountRepository;
    public JwtSecurityConfig(UserPrincipalDetailsService
                                     userPrincipalDetailsService, AccountRepository accountRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.accountRepository = accountRepository;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new
                DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new
                BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new
                        JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new
                        JwtAuthorizationFilter(authenticationManager(), this.accountRepository))
                .authorizeRequests()
                .antMatchers("/account/**").permitAll()
                .antMatchers("/live/auth").permitAll()
                .antMatchers("/chatws").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().fullyAuthenticated();
    }
}
