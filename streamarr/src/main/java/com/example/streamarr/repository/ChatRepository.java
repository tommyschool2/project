package com.example.streamarr.repository;

import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

}