package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long merchantId;
    private Long customerId;
    private BigDecimal amount;
    private Currency currency;
    private String status;
    private String idempotencyKey;
    private Instant createdAt;
    private String metadata;
    private String authCode;
}
