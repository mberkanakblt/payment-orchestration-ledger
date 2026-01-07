package com.mehmetberkan.paycore.domain.model;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.enums.EntryType;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tblledger")
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "ledger_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "ledger_currency"))
    })
    private Money amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Instant createdAt;

    private Long pairId;

    private String txnRef;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
