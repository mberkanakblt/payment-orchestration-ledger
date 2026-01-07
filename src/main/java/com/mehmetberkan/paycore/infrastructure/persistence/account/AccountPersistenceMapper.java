package com.mehmetberkan.paycore.infrastructure.persistence.account;

import com.mehmetberkan.paycore.domain.aggregate.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceMapper {

    public AccountJpaEntity toEntity(Account account) {
        AccountJpaEntity entity = new AccountJpaEntity();
        entity.setId(account.getId());
        entity.setIban(account.getIban());
        entity.setCurrency(account.getCurrency());
        entity.setBalance(account.getBalance());
        entity.setStatus(account.getStatus());
        entity.setVersion(account.getVersion());
        return entity;
    }

    public Account toDomain(AccountJpaEntity entity){
        return new Account(
                entity.getId(),
                entity.getIban(),
                entity.getCurrency(),
                entity.getBalance(),
                entity.getStatus(),
                entity.getVersion()
        );
    }
}
