package com.mehmetberkan.paycore.infrastructure.persistence.ledger;

import com.mehmetberkan.paycore.application.port.out.LedgerPort;
import com.mehmetberkan.paycore.domain.aggregate.Ledger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LedgerPersistenceAdapter implements LedgerPort {

    private final LedgerJpaRepository ledgerJpaRepository;
    private final LedgerPersistenceMapper mapper;

    @Override
    public Ledger save(Ledger ledger) {
        LedgerJpaEntity entity = mapper.toEntity(ledger);
        LedgerJpaEntity savedEntity = ledgerJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Ledger> findByAccountId(Long accountId) {
        return ledgerJpaRepository.findByAccountId(accountId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Ledger> findByPairId(Long pairId) {
        return ledgerJpaRepository.findByPairId(pairId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Page<Ledger> findByAccountIdPaged(Long accountId, Pageable pageable) {
        return ledgerJpaRepository.findByAccountIdOrderByCreatedAtDesc(accountId, pageable)
                .map(mapper::toDomain);
    }
}
