package com.mehmetberkan.paycore.application.port.in;

import com.mehmetberkan.paycore.api.dto.response.LedgerEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LedgerUseCase {
    Page<LedgerEntryDto> getLedgerByAccountId(Long accountId, Pageable pageable);
}
