package com.mehmetberkan.paycore.infrastructure.persistence.ledger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerJpaRepository extends JpaRepository<LedgerJpaEntity, Long> {
    List<LedgerJpaEntity> findByAccountId(Long accountId);
    List<LedgerJpaEntity> findByPairId(Long pairId);
    Page<LedgerJpaEntity> findByAccountIdOrderByCreatedAtDesc(Long accountId, Pageable pageable);
}
