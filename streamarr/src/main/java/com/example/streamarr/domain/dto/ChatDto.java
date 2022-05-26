package com.example.streamarr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ChatDto implements Serializable {
    private final int id;
    private final AccountDto owner;
    private final Set<ChatModeratorDto> items;
}
