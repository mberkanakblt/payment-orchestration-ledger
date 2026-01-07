package com.mehmetberkan.paycore.infrastructure.persistence.payment;

import com.mehmetberkan.paycore.domain.aggregate.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceMapper {

    public PaymentJpaEntity toEntity(Payment payment) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        entity.setId(payment.getId());
        entity.setMerchantId(payment.getMerchantId());
        entity.setCustomerId(payment.getCustomerId());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());
        entity.setIdempotencyKey(payment.getIdempotencyKey());
        entity.setCreatedAt(payment.getCreatedAt());
        entity.setMetadata(payment.getMetadata());
        entity.setAuthCode(payment.getAuthCode());
        return entity;
    }

    public Payment toDomain(PaymentJpaEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .merchantId(entity.getMerchantId())
                .customerId(entity.getCustomerId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .idempotencyKey(entity.getIdempotencyKey())
                .createdAt(entity.getCreatedAt())
                .metadata(entity.getMetadata())
                .authCode(entity.getAuthCode())
                .build();
    }
}

