package com.mehmetberkan.paycore.infrastructure.persistence.transfer;

import com.mehmetberkan.paycore.domain.model.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferPersistenceMapper {

    public TransferJpaEntity toEntity(Transfer transfer) {
        TransferJpaEntity entity = new TransferJpaEntity();
        entity.setId(transfer.getId());
        entity.setFromAccountId(transfer.getFromAccountId());
        entity.setToAccountId(transfer.getToAccountId());
        entity.setAmount(transfer.getAmount());
        entity.setStatus(transfer.getStatus());
        entity.setIdempotencyKey(transfer.getIdempotencyKey());
        entity.setCreatedAt(transfer.getCreatedAt());
        return entity;
    }

    public Transfer toDomain(TransferJpaEntity entity) {
        return Transfer.builder()
                .id(entity.getId())
                .fromAccountId(entity.getFromAccountId())
                .toAccountId(entity.getToAccountId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .idempotencyKey(entity.getIdempotencyKey())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
