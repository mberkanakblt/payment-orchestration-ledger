package com.mehmetberkan.paycore.api.dto.response;

import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.enums.EntryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LedgerEntryDto {
    private Long id;
    private EntryType entryType;
    private Long accountId;
    private BigDecimal amount;
    private Currency currency;
    private Instant createdAt;
    private Long pairId;
    private String txnRef;
}
