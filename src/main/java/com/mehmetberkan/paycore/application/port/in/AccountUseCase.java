package com.mehmetberkan.paycore.application.port.in;

import com.mehmetberkan.paycore.api.dto.command.CreateAccountCommand;
import com.mehmetberkan.paycore.api.dto.response.AccountDto;

public interface AccountUseCase {
    AccountDto createAccount(CreateAccountCommand command);
    AccountDto getAccountById(Long id);
}
