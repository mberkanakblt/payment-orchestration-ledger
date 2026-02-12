package com.mehmetberkan.paycore.infrastructure.persistence.ledger;

import com.mehmetberkan.paycore.domain.aggregate.Ledger;
import org.springframework.stereotype.Component;

@Component
public class LedgerPersistenceMapper {

    public LedgerJpaEntity toEntity(Ledger ledger) {
        LedgerJpaEntity entity = new LedgerJpaEntity();
        entity.setId(ledger.getId());
        entity.setEntryType(ledger.getEntryType());
        entity.setAccountId(ledger.getAccountId());
        entity.setAmount(ledger.getAmount());
        entity.setCurrency(ledger.getCurrency());
        entity.setCreatedAt(ledger.getCreatedAt());
        entity.setPairId(ledger.getPairId());
        entity.setTxnRef(ledger.getTxnRef());
        return entity;
    }

    public Ledger toDomain(LedgerJpaEntity entity) {
        return Ledger.builder()
                .id(entity.getId())
                .entryType(entity.getEntryType())
                .accountId(entity.getAccountId())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .pairId(entity.getPairId())
                .txnRef(entity.getTxnRef())
                .build();
    }
}
