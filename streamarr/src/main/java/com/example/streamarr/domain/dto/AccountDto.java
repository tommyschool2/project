package com.example.streamarr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class AccountDto implements Serializable {
    private final String username;
    private final String password;
    private final String color;
    private final String token;
}
