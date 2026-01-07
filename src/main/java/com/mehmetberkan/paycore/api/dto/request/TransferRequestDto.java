package com.mehmetberkan.paycore.api.dto.request;

import com.mehmetberkan.paycore.domain.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDto(
        @NotNull
        Long fromAccountId,
        @NotNull
        Long toAccountId,
        @NotNull
        @Positive
        BigDecimal amount,
        @NotNull
        Currency currency
) {
}
