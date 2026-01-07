package com.mehmetberkan.paycore.domain.aggregate;

import com.mehmetberkan.paycore.domain.exception.DomainException;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import com.mehmetberkan.paycore.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Long id;
    private Long merchantId;
    private Long customerId;
    private Money amount;
    private PaymentStatus status;
    private String idempotencyKey;
    private Instant createdAt;
    private String metadata;
    private String authCode;

    public static Payment authorize(
            Long merchantId,
            Long customerId,
            Money amount,
            String authCode,
            String idempotencyKey,
            String metadata
    ) {
        Payment payment = new Payment();
        payment.merchantId = merchantId;
        payment.customerId = customerId;
        payment.amount = amount;
        payment.authCode = authCode;
        payment.idempotencyKey = idempotencyKey;
        payment.metadata = metadata;
        payment.status = PaymentStatus.AUTHORIZED;
        payment.createdAt = Instant.now();
        return payment;
    }

    public void capture() {
        if (!status.canCapture()) {
            throw new DomainException("Payment cannot be captured from status: " + status);
        }
        this.status = PaymentStatus.CAPTURED;
    }

    public void refund() {
        if (!status.canRefund()) {
            throw new DomainException("Payment cannot be refunded from status: " + status);
        }
        this.status = PaymentStatus.REFUNDED;
    }

    public void voidPayment() {
        if (!status.canVoid()) {
            throw new DomainException("Payment cannot be voided from status: " + status);
        }
        this.status = PaymentStatus.VOID;
    }
}
