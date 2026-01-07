package com.mehmetberkan.paycore.api.dto.command;

import com.mehmetberkan.paycore.domain.valueobject.Money;

public record TransferCommand(
        Long fromAccountId,
        Long toAccountId,
        Money amount,
        String key
) {
}
