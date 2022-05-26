package com.example.streamarr.service;

import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.dto.AccountDto;
import com.example.streamarr.domain.dto.ChatDto;

import java.util.UUID;

public interface ChatService {

    ChatDto createChat(Account account);
    boolean addModerator(Account account, int accountId);

}
