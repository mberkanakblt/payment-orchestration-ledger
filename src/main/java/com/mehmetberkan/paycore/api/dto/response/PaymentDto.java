package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Long merchantId;
    private Long customerId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentStatus.Status status;
    private String idempotencyKey;
    private Instant createdAt;
    private String metadata;
    private String authCode;
}

