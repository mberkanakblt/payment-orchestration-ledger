package com.mehmetberkan.paycore.api.dto.command;

import com.mehmetberkan.paycore.domain.enums.Currency;

import java.math.BigDecimal;

public record CreateAccountCommand(
        Currency currency,
        BigDecimal initialBalance
) {
}
