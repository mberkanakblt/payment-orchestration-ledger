package com.mehmetberkan.paycore.infrastructure.persistence.transfer;

import com.mehmetberkan.paycore.application.port.out.TransferPort;
import com.mehmetberkan.paycore.domain.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferPort {

    private final TransferJpaRepository transferJpaRepository;
    private final TransferPersistenceMapper mapper;

    @Override
    public Transfer save(Transfer transfer) {
        TransferJpaEntity entity = mapper.toEntity(transfer);
        TransferJpaEntity savedEntity = transferJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    public Optional<Transfer> findByIdempotencyKey(String idempotencyKey) {
        return transferJpaRepository.findByIdempotencyKey(idempotencyKey)
                .map(mapper::toDomain);
    }
}
