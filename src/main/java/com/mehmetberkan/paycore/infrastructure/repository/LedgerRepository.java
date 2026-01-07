package com.mehmetberkan.paycore.infrastructure.repository;

import com.mehmetberkan.paycore.domain.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByPairId(Long pairId);
    List<LedgerEntry> findByAccountId(Long accountId);
}

