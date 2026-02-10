package com.mehmetberkan.paycore.domain.aggregate;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.enums.EntryType;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ledger {
    private Long id;
    private EntryType entryType;
    private Long accountId;
    private Money amount;
    private Currency currency;
    private Instant createdAt;
    private Long pairId;
    private String txnRef;

    public static Ledger createDebitEntry(Long accountId, Money amount, Long pairId, String txnRef) {
        return Ledger.builder()
                .entryType(EntryType.DEBIT)
                .accountId(accountId)
                .amount(amount)
                .currency(amount.getCurrency())
                .pairId(pairId)
                .txnRef(txnRef)
                .createdAt(Instant.now())
                .build();
    }

    public static Ledger createCreditEntry(Long accountId, Money amount, Long pairId, String txnRef) {
        return Ledger.builder()
                .entryType(EntryType.CREDIT)
                .accountId(accountId)
                .amount(amount)
                .currency(amount.getCurrency())
                .pairId(pairId)
                .txnRef(txnRef)
                .createdAt(Instant.now())
                .build();
    }
}
