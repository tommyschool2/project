package com.example.streamarr.service.mapper;

import com.example.streamarr.domain.Chat;
import com.example.streamarr.domain.ChatModerator;
import com.example.streamarr.domain.dto.ChatDto;
import com.example.streamarr.domain.dto.ChatModeratorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatModeratorMapper extends EntityMapper<ChatModeratorDto, ChatModerator> {
}
