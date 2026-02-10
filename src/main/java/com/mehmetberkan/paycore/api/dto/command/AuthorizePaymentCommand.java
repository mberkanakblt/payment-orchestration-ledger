package com.mehmetberkan.paycore.api.dto.command;

import com.mehmetberkan.paycore.domain.valueobject.Money;

public record AuthorizePaymentCommand(
        Long merchantId,
        Long customerId,
        Money amount,
        String authCode,
        String idempotencyKey,
        String metadata
) {
}