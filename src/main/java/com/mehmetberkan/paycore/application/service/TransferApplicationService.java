package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.api.dto.command.TransferCommand;
import com.mehmetberkan.paycore.api.dto.response.TransferDto;
import com.mehmetberkan.paycore.application.port.in.TransferUseCase;
import com.mehmetberkan.paycore.application.port.out.AccountPort;
import com.mehmetberkan.paycore.application.port.out.TransferPort;
import com.mehmetberkan.paycore.domain.aggregate.Account;
import com.mehmetberkan.paycore.domain.enums.Status;
import com.mehmetberkan.paycore.domain.model.IdempotencyRecord;
import com.mehmetberkan.paycore.domain.model.Transfer;
import com.mehmetberkan.paycore.infrastructure.persistence.transfer.TransferPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferApplicationService implements TransferUseCase {
    private final TransferPort transferPort;
    private final AccountPort accountPort;
    private final IdempotencyService idempotencyService;
    private final TransferPersistenceAdapter transferPersistenceAdapter;

    @Override
    @Transactional
    public TransferDto transfer(TransferCommand command) {
        String idempotencyKey = command.key();

        Optional<IdempotencyRecord> existingRecord = idempotencyService.findByKey(idempotencyKey);
        if (existingRecord.isPresent()) {
            IdempotencyRecord record = existingRecord.get();

            if (record.getStatus() == Status.COMPLETED) {
                return findTransferByIdempotencyKey(idempotencyKey);
            }
            if (record.getStatus() == Status.PROCESSING) {
                throw new RuntimeException("Request is already being processed. Please wait.");
            }
        }

        String requestHash = generateRequestHash(command);
        idempotencyService.createProcessing(idempotencyKey, requestHash);

        Account fromAccount = accountPort.findById(command.fromAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found: " + command.fromAccountId()));
        Account toAccount = accountPort.findById(command.toAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found: " + command.toAccountId()));

        Transfer transfer = Transfer.builder()
                .fromAccountId(fromAccount.getId())
                .toAccountId(toAccount.getId())
                .amount(command.amount())
                .status(Status.PROCESSING)
                .idempotencyKey(idempotencyKey)
                .createdAt(java.time.Instant.now())
                .build();

        try {
            fromAccount.debit(command.amount());
            toAccount.credit(command.amount());

            accountPort.save(fromAccount);
            accountPort.save(toAccount);

            transfer.setStatus(Status.COMPLETED);

            Transfer savedTransfer = transferPort.save(transfer);

            idempotencyService.markCompleted(idempotencyKey, String.valueOf(savedTransfer.getId()));

            return toDto(savedTransfer);

        } catch (Exception exception) {
            transfer.setStatus(Status.FAILED);
            transferPort.save(transfer);
            idempotencyService.markFailed(idempotencyKey);
            throw exception;
        }
    }

    private TransferDto findTransferByIdempotencyKey(String idempotencyKey) {
        return transferPersistenceAdapter.findByIdempotencyKey(idempotencyKey)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Transfer not found: " + idempotencyKey));
    }

    private String generateRequestHash(TransferCommand command) {
        return command.fromAccountId() + "-" + command.toAccountId() + "-" +
                command.amount().getAmount() + "-" + command.amount().getCurrency();
    }

    @Override
    public TransferDto getTransferById(Long id) {
        return transferPort.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Transfer not found: " + id));
    }

    private TransferDto toDto(Transfer transfer) {
        return TransferDto.builder()
                .id(transfer.getId())
                .fromAccountId(transfer.getFromAccountId())
                .toAccountId(transfer.getToAccountId())
                .amount(transfer.getAmount().getAmount())
                .currency(transfer.getAmount().getCurrency())
                .status(transfer.getStatus())
                .idempotencyKey(transfer.getIdempotencyKey())
                .createdAt(transfer.getCreatedAt())
                .build();
    }
}
