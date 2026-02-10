package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.api.dto.response.LedgerEntryDto;
import com.mehmetberkan.paycore.application.port.in.LedgerUseCase;
import com.mehmetberkan.paycore.application.port.out.LedgerPort;
import com.mehmetberkan.paycore.domain.aggregate.Ledger;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService implements LedgerUseCase {

    private final LedgerPort ledgerPort;

    @Transactional
    public void createDoubleEntry(Long debitAccountId, Long creditAccountId, Money amount, String txnRef) {
        Long pairId = System.currentTimeMillis();

        Ledger debitEntry = Ledger.createDebitEntry(debitAccountId, amount, pairId, txnRef);
        Ledger creditEntry = Ledger.createCreditEntry(creditAccountId, amount, pairId, txnRef);

        ledgerPort.save(debitEntry);
        ledgerPort.save(creditEntry);
    }

    public List<Ledger> getEntriesByAccountId(Long accountId) {
        return ledgerPort.findByAccountId(accountId);
    }

    public List<Ledger> getEntriesByPairId(Long pairId) {
        return ledgerPort.findByPairId(pairId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LedgerEntryDto> getLedgerByAccountId(Long accountId, Pageable pageable) {
        return ledgerPort.findByAccountIdPaged(accountId, pageable)
                .map(this::toDto);
    }

    private LedgerEntryDto toDto(Ledger ledger) {
        return LedgerEntryDto.builder()
                .id(ledger.getId())
                .entryType(ledger.getEntryType())
                .accountId(ledger.getAccountId())
                .amount(ledger.getAmount().getAmount())
                .currency(ledger.getCurrency())
                .createdAt(ledger.getCreatedAt())
                .pairId(ledger.getPairId())
                .txnRef(ledger.getTxnRef())
                .build();
    }
}
