package com.mehmetberkan.paycore.domain.model;

import com.mehmetberkan.paycore.domain.enums.HoldStatus;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import jakarta.annotation.Nullable;
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
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long accountId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "hold_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "hold_currency"))
    })
    private Money amount;

    @Enumerated(EnumType.STRING)
    private HoldStatus status;

    private Instant createdAt;
    private Instant releasedAt;

}
