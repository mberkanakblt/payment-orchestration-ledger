package com.mehmetberkan.paycore.application.port.in;

import com.mehmetberkan.paycore.api.dto.command.AuthorizePaymentCommand;
import com.mehmetberkan.paycore.api.dto.response.PaymentDto;

public interface PaymentUseCase {
    PaymentDto authorize(AuthorizePaymentCommand command);
    PaymentDto capture(Long paymentId);
    PaymentDto voidPayment(Long paymentId);
    PaymentDto refund(Long paymentId);
    PaymentDto getPaymentById(Long paymentId);
}

