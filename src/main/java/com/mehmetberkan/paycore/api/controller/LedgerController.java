package com.mehmetberkan.paycore.api.controller;

import com.mehmetberkan.paycore.api.dto.response.LedgerEntryDto;
import com.mehmetberkan.paycore.application.port.in.LedgerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/v1/ledger")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LedgerController {

    private final LedgerUseCase ledgerUseCase;

    @GetMapping("/{accountId}")
    public ResponseEntity<Page<LedgerEntryDto>> getLedgerByAccountId(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LedgerEntryDto> ledgerEntries = ledgerUseCase.getLedgerByAccountId(accountId, pageable);
        return ResponseEntity.ok(ledgerEntries);
    }
}
