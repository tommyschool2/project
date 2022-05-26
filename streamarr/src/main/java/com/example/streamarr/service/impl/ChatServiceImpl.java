package com.example.streamarr.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.Chat;
import com.example.streamarr.domain.ChatModerator;
import com.example.streamarr.domain.dto.AccountDto;
import com.example.streamarr.domain.dto.ChatDto;
import com.example.streamarr.repository.AccountRepository;
import com.example.streamarr.repository.ChatModeratorRepository;
import com.example.streamarr.repository.ChatRepository;
import com.example.streamarr.security.JwtProperties;
import com.example.streamarr.service.AccountService;
import com.example.streamarr.service.ChatService;
import com.example.streamarr.service.mapper.AccountMapper;
import com.example.streamarr.service.mapper.ChatMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final AccountRepository accountRepository;
    private final ChatModeratorRepository chatModeratorRepository;

    public ChatServiceImpl(ChatMapper chatMapper, ChatRepository chatRepository, AccountRepository accountRepository, ChatModeratorRepository chatModeratorRepository) {
        this.chatMapper = chatMapper;
        this.chatRepository = chatRepository;
        this.accountRepository = accountRepository;
        this.chatModeratorRepository = chatModeratorRepository;
    }

    @Override
    public ChatDto createChat(Account account) {
        Chat chat = new Chat();
        chat.setOwner(account);
        chat = chatRepository.save(chat);
        return chatMapper.toDto(chat);
    }

    @Override
    public boolean addModerator(Account account, int accountId) {
        Account moderator = accountRepository.getById(accountId);
        if (account == null) return false;

        ChatModerator chatModerator = new ChatModerator();
        chatModerator.setChat(account.getChat());
        chatModerator.setModerator(moderator);
        chatModeratorRepository.save(chatModerator);
        return true;
    }
}
