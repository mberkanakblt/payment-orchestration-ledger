package com.mehmetberkan.paycore.api.dto.request;


import com.mehmetberkan.paycore.domain.enums.Currency;

import java.math.BigDecimal;

public record CreateAccountRequest(
        Currency currency,
        BigDecimal initialBalance
) {
}
