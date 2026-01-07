package com.mehmetberkan.paycore.application.port.out;

import com.mehmetberkan.paycore.domain.model.Transfer;

import java.util.Optional;

public interface TransferPort {
    Transfer save(Transfer transfer);
    Optional<Transfer> findById(Long id);
}
