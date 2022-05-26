package com.example.streamarr.service.mapper;

import com.example.streamarr.domain.Chat;
import com.example.streamarr.domain.dto.ChatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends EntityMapper<ChatDto, Chat> {
}
