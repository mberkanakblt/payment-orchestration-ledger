package com.mehmetberkan.paycore.infrastructure.persistence.payment;

import com.mehmetberkan.paycore.domain.valueobject.Money;
import com.mehmetberkan.paycore.domain.valueobject.PaymentStatus;
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
@Table(name = "tblpayment")
public class PaymentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long merchantId;

    @Column(nullable = false)
    private Long customerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "payment_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "payment_currency"))
    })
    private Money amount;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private PaymentStatus status;

    @Column(nullable = false, unique = true)
    private String idempotencyKey;

    private Instant createdAt;

    private String metadata;

    private String authCode;
}
