package com.example.streamarr.security;

import com.example.streamarr.domain.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager
                                           authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }
    /* trigger when we issue POST request to /login We also need to pass
    in {"username":"","password":""} in the request body */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest
                                                        request, HttpServletResponse response) throws AuthenticationException {
        AccountDto credentials = null;
        try {
            credentials = new
                    ObjectMapper().readValue(request.getInputStream(), AccountDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(
                credentials != null ? credentials.getUsername() : null,
                credentials != null ? credentials.getPassword() : null,
                new ArrayList<>()
        );
// authentication user
        return authenticationManager.authenticate(authenticationToken);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed) throws
            IOException, ServletException {
        throw new IllegalArgumentException("Error login");
    }
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response, FilterChain chain, Authentication
//                                                    authResult) throws IOException, ServletException {
////Grad principal
//        UserPrincipal principal = (UserPrincipal)
//                authResult.getPrincipal();
////Create JWT Token
//        String token = JWT.create()
//                .withSubject(principal.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() +
//                        JwtProperties.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
////Add token in response
//        response.addHeader(
//                "Access-Control-Allow-Headers",
//                "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Customer-header"
//        );
//        response.addHeader(JwtProperties.HEADER_STRING,
//                JwtProperties.TOKEN_PREFIX + token);
////response.addHeader("username", principal.getUsername());
//    }
}