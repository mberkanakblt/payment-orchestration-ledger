package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String iban;
    private Currency currency;
    private BigDecimal balance;
    private AccountStatus status;
}