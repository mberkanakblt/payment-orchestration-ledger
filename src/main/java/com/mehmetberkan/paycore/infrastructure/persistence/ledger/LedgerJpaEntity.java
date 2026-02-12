package com.mehmetberkan.paycore.infrastructure.persistence.ledger;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.enums.EntryType;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblledger")
public class LedgerJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;

    @Column(nullable = false)
    private Long accountId;

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
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
