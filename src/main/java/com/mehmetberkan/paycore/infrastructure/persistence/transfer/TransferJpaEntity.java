package com.mehmetberkan.paycore.infrastructure.persistence.transfer;

import com.mehmetberkan.paycore.domain.enums.Status;
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
@Table(name = "tbltransfer")
public class TransferJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fromAccountId;

    @Column(nullable = false)
    private Long toAccountId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transfer_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transfer_currency"))
    })
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, unique = true)
    private String idempotencyKey;

    private Instant createdAt;
}

