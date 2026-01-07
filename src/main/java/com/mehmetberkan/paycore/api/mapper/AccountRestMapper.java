package com.mehmetberkan.paycore.api.mapper;

import com.mehmetberkan.paycore.api.dto.command.CreateAccountCommand;
import com.mehmetberkan.paycore.api.dto.request.CreateAccountRequest;
import com.mehmetberkan.paycore.api.dto.response.AccountResponseDto;
import com.mehmetberkan.paycore.api.dto.response.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountRestMapper {

    public CreateAccountCommand toCommand(CreateAccountRequest dto){
        return new CreateAccountCommand(
                dto.currency(),
                dto.initialBalance()
        );
    }
    public AccountResponseDto toResponse(AccountDto dto){
        return new AccountResponseDto(
                dto.getId(),
                dto.getIban(),
                dto.getCurrency(),
                dto.getBalance(),
                dto.getStatus()
        );
    }

}
