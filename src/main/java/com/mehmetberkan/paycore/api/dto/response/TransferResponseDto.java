package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferResponseDto(
        Long id,
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        Currency currency,
        Status status,
        Instant createdAt
) {
}
