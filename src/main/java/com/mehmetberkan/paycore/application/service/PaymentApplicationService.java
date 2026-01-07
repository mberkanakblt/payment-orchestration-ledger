package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.api.dto.command.AuthorizePaymentCommand;
import com.mehmetberkan.paycore.api.dto.response.PaymentDto;
import com.mehmetberkan.paycore.application.port.in.PaymentUseCase;
import com.mehmetberkan.paycore.application.port.out.AccountPort;
import com.mehmetberkan.paycore.application.port.out.PaymentPort;
import com.mehmetberkan.paycore.domain.aggregate.Account;
import com.mehmetberkan.paycore.domain.aggregate.Payment;
import com.mehmetberkan.paycore.domain.exception.DomainException;
import com.mehmetberkan.paycore.domain.valueobject.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService implements PaymentUseCase {

    private final PaymentPort paymentPort;
    private final AccountPort accountPort;
    private final HoldService holdService;
    private final LedgerService ledgerService;

    @Override
    @Transactional
    public PaymentDto authorize(AuthorizePaymentCommand command) {
        Account customerAccount = accountPort.findById(command.customerId())
                .orElseThrow(() -> new DomainException("Customer account not found: " + command.customerId()));

        if (customerAccount.getBalance().isLessThan(command.amount())) {
            Payment declinedPayment = Payment.authorize(
                    command.merchantId(),
                    command.customerId(),
                    command.amount(),
                    command.authCode(),
                    command.idempotencyKey(),
                    command.metadata()
            );
            declinedPayment.setStatus(PaymentStatus.DECLINED);
            return toDto(paymentPort.save(declinedPayment));
        }

        Payment payment = Payment.authorize(
                command.merchantId(),
                command.customerId(),
                command.amount(),
                command.authCode(),
                command.idempotencyKey(),
                command.metadata()
        );
        Payment savedPayment = paymentPort.save(payment);

        holdService.createHold(command.customerId(), savedPayment.getId(), command.amount());

        return toDto(savedPayment);
    }

    @Override
    @Transactional
    public PaymentDto capture(Long paymentId) {
        Payment payment = paymentPort.findById(paymentId)
                .orElseThrow(() -> new DomainException("Payment not found: " + paymentId));

        payment.capture();

        holdService.captureHold(paymentId);

        Account customerAccount = accountPort.findById(payment.getCustomerId())
                .orElseThrow(() -> new DomainException("Customer account not found"));
        customerAccount.debit(payment.getAmount());
        accountPort.save(customerAccount);

        String txnRef = "CAPTURE-" + paymentId;
        ledgerService.createDoubleEntry(payment.getCustomerId(), payment.getMerchantId(), payment.getAmount(), txnRef);

        return toDto(paymentPort.save(payment));
    }

    @Override
    @Transactional
    public PaymentDto voidPayment(Long paymentId) {
        Payment payment = paymentPort.findById(paymentId)
                .orElseThrow(() -> new DomainException("Payment not found: " + paymentId));

        payment.voidPayment();

        holdService.releaseHold(paymentId);

        return toDto(paymentPort.save(payment));
    }

    @Override
    @Transactional
    public PaymentDto refund(Long paymentId) {
        Payment payment = paymentPort.findById(paymentId)
                .orElseThrow(() -> new DomainException("Payment not found: " + paymentId));

        payment.refund();

        Account customerAccount = accountPort.findById(payment.getCustomerId())
                .orElseThrow(() -> new DomainException("Customer account not found"));
        customerAccount.credit(payment.getAmount());
        accountPort.save(customerAccount);

        String txnRef = "REFUND-" + paymentId;
        ledgerService.createDoubleEntry(payment.getMerchantId(), payment.getCustomerId(), payment.getAmount(), txnRef);

        return toDto(paymentPort.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentPort.findById(paymentId)
                .orElseThrow(() -> new DomainException("Payment not found: " + paymentId));
        return toDto(payment);
    }

    private PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .merchantId(payment.getMerchantId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount().getAmount())
                .currency(payment.getAmount().getCurrency())
                .status(payment.getStatus().getValue())
                .idempotencyKey(payment.getIdempotencyKey())
                .createdAt(payment.getCreatedAt())
                .metadata(payment.getMetadata())
                .authCode(payment.getAuthCode())
                .build();
    }
}
