package com.mehmetberkan.paycore.api.mapper;

import com.mehmetberkan.paycore.api.dto.command.AuthorizePaymentCommand;
import com.mehmetberkan.paycore.api.dto.request.AuthorizePaymentRequest;
import com.mehmetberkan.paycore.api.dto.response.PaymentDto;
import com.mehmetberkan.paycore.api.dto.response.PaymentResponseDto;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class PaymentRestMapper {

    public AuthorizePaymentCommand toCommand(AuthorizePaymentRequest request, String idempotencyKey, String authCode) {
        Money amount = new Money(request.amount(), request.currency());
        return new AuthorizePaymentCommand(
                request.merchantId(),
                request.customerId(),
                amount,
                authCode,
                idempotencyKey,
                request.metadata()
        );
    }

    public PaymentResponseDto toResponse(PaymentDto dto) {
        return new PaymentResponseDto(
                dto.getId(),
                dto.getMerchantId(),
                dto.getCustomerId(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getStatus().name(),
                dto.getIdempotencyKey(),
                dto.getCreatedAt(),
                dto.getMetadata(),
                dto.getAuthCode()
        );
    }
}
