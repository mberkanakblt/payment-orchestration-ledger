package com.mehmetberkan.paycore.api.dto.request;

import com.mehmetberkan.paycore.domain.enums.Currency;

import java.math.BigDecimal;

public record AuthorizePaymentRequest(
        Long merchantId,
        Long customerId,
        BigDecimal amount,
        Currency currency,
        String metadata
) {}

