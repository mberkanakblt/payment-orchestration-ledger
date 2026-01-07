package com.mehmetberkan.paycore.infrastructure.persistence.account;

import com.mehmetberkan.paycore.application.port.out.AccountPort;
import com.mehmetberkan.paycore.domain.aggregate.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPort {

    private final AccountJpaRepository accountRepository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Account save(Account account) {
        AccountJpaEntity entity = mapper.toEntity(account);
        AccountJpaEntity saved = accountRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id)
                .map(mapper::toDomain);
    }
}
