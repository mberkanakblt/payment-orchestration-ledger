package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.enums.Currency;

import java.math.BigDecimal;

public record AccountResponseDto (
        Long id,
        String iban,
        Currency currency,
        BigDecimal balance,
        AccountStatus status
){
}
