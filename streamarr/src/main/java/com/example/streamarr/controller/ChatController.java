package com.example.streamarr.controller;

import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.dto.AccountDto;
import com.example.streamarr.domain.dto.ModeratorDto;
import com.example.streamarr.security.UserPrincipal;
import com.example.streamarr.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/addmoderator")
    public ResponseEntity<AccountDto> createAccount(@RequestBody ModeratorDto addModeratorDto) {
        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = principal.getAccount();
        boolean added = chatService.addModerator(account, addModeratorDto.getId());
        if (!added) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
