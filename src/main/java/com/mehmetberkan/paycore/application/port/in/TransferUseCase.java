package com.mehmetberkan.paycore.application.port.in;

import com.mehmetberkan.paycore.api.dto.command.TransferCommand;
import com.mehmetberkan.paycore.api.dto.response.TransferDto;

public interface TransferUseCase {
    TransferDto transfer(TransferCommand command);
    TransferDto getTransferById(Long id);
}
