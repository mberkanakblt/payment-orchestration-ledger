package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.api.dto.command.CreateAccountCommand;
import com.mehmetberkan.paycore.api.dto.response.AccountDto;
import com.mehmetberkan.paycore.application.port.in.AccountUseCase;
import com.mehmetberkan.paycore.application.port.out.AccountPort;
import com.mehmetberkan.paycore.domain.aggregate.Account;
import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.exception.DomainException;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountApplicationService implements AccountUseCase {
    private final AccountPort accountPort;

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountCommand command) {
        String iban = UUID.randomUUID().toString();

        Money initialBalance = new Money(command.initialBalance(),
                command.currency());

        Account account = new Account(
                null,
                iban,
                command.currency(),
                initialBalance,
                AccountStatus.ACTIVE,
                null
        );
        Account saved = accountPort.save(account);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long id) {
        Account account = accountPort.findById(id)
                .orElseThrow(() -> new DomainException("Account not found"));
        return toDto(account);
    }

    private AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .currency(account.getCurrency())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus())
                .build();
    }
}
