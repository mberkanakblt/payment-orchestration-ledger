package com.mehmetberkan.paycore.application.port.out;

import com.mehmetberkan.paycore.domain.aggregate.Ledger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LedgerPort {
    Ledger save(Ledger ledger);
    List<Ledger> findByAccountId(Long accountId);
    List<Ledger> findByPairId(Long pairId);
    Page<Ledger> findByAccountIdPaged(Long accountId, Pageable pageable);
}
