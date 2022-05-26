package com.example.streamarr.domain.dto;

import com.example.streamarr.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ChatModeratorDto implements Serializable {
    private final int id;
    private final Chat chat;
    private final AccountDto moderator;
}
