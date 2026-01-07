package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.domain.enums.EntryType;
import com.mehmetberkan.paycore.domain.model.LedgerEntry;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import com.mehmetberkan.paycore.infrastructure.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    @Transactional
    public void createDoubleEntry(Long debitAccountId, Long creditAccountId, Money amount, String txnRef) {
        Long pairId = System.currentTimeMillis();

        LedgerEntry debitEntry = LedgerEntry.builder()
                .entryType(EntryType.DEBIT)
                .accountId(debitAccountId)
                .amount(amount)
                .currency(amount.getCurrency())
                .pairId(pairId)
                .txnRef(txnRef)
                .build();

        LedgerEntry creditEntry = LedgerEntry.builder()
                .entryType(EntryType.CREDIT)
                .accountId(creditAccountId)
                .amount(amount)
                .currency(amount.getCurrency())
                .pairId(pairId)
                .txnRef(txnRef)
                .build();

        ledgerRepository.save(debitEntry);
        ledgerRepository.save(creditEntry);
    }

    public List<LedgerEntry> getEntriesByAccountId(Long accountId) {
        return ledgerRepository.findByAccountId(accountId);
    }

    public List<LedgerEntry> getEntriesByPairId(Long pairId) {
        return ledgerRepository.findByPairId(pairId);
    }
}
