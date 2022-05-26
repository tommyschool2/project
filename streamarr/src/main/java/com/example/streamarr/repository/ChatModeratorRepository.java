package com.example.streamarr.repository;

import com.example.streamarr.domain.Chat;
import com.example.streamarr.domain.ChatModerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatModeratorRepository extends JpaRepository<ChatModerator, UUID> {

}