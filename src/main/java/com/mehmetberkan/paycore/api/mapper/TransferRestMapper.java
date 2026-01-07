package com.mehmetberkan.paycore.api.mapper;

import com.mehmetberkan.paycore.api.dto.command.TransferCommand;
import com.mehmetberkan.paycore.api.dto.request.TransferRequestDto;
import com.mehmetberkan.paycore.api.dto.response.TransferDto;
import com.mehmetberkan.paycore.api.dto.response.TransferResponseDto;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class TransferRestMapper {

    public TransferCommand toCommand(TransferRequestDto dto, String key) {
        Money money = new Money(
                dto.amount(),
                dto.currency()
        );
        return new TransferCommand(
                dto.fromAccountId(),
                dto.toAccountId(),
                money,
                key
        );
    }
    public TransferResponseDto toResponse(TransferDto dto){
        return new TransferResponseDto(
                dto.getId(),
                dto.getFromAccountId(),
                dto.getToAccountId(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getStatus(),
                dto.getCreatedAt()
        );
    }
}
