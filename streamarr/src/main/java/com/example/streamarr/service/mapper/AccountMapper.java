package com.example.streamarr.service.mapper;

import com.example.streamarr.domain.Account;
import com.example.streamarr.domain.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDto, Account> {
}
