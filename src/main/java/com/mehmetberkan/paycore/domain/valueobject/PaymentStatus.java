package com.mehmetberkan.paycore.domain.valueobject;

import com.mehmetberkan.paycore.domain.exception.DomainException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentStatus {

    public enum Status {
        AUTHORIZED,
        CAPTURED,
        VOID,
        REFUNDED,
        DECLINED
    }

    public static final PaymentStatus AUTHORIZED = new PaymentStatus(Status.AUTHORIZED);
    public static final PaymentStatus CAPTURED = new PaymentStatus(Status.CAPTURED);
    public static final PaymentStatus VOID = new PaymentStatus(Status.VOID);
    public static final PaymentStatus REFUNDED = new PaymentStatus(Status.REFUNDED);
    public static final PaymentStatus DECLINED = new PaymentStatus(Status.DECLINED);

    private static final Set<Status> CAN_CAPTURE_FROM = EnumSet.of(Status.AUTHORIZED);
    private static final Set<Status> CAN_REFUND_FROM = EnumSet.of(Status.CAPTURED);
    private static final Set<Status> CAN_VOID_FROM = EnumSet.of(Status.AUTHORIZED);

    private Status value;

    private PaymentStatus(Status value) {
        this.value = value;
    }

    public static PaymentStatus of(Status status) {
        return switch (status) {
            case AUTHORIZED -> AUTHORIZED;
            case CAPTURED -> CAPTURED;
            case VOID -> VOID;
            case REFUNDED -> REFUNDED;
            case DECLINED -> DECLINED;
        };
    }

    public static PaymentStatus fromString(String value) {
        try {
            return of(Status.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new DomainException("Invalid payment status: " + value);
        }
    }

    public boolean canCapture() {
        return CAN_CAPTURE_FROM.contains(this.value);
    }

    public boolean isDeclined() {
        return this.value == Status.DECLINED;
    }

    public boolean canRefund() {
        return CAN_REFUND_FROM.contains(this.value);
    }

    public boolean canVoid() {
        return CAN_VOID_FROM.contains(this.value);
    }

    public PaymentStatus capture() {
        if (!canCapture()) {
            throw new DomainException("Payment cannot be captured from status: " + this.value);
        }
        return CAPTURED;
    }

    public PaymentStatus refund() {
        if (!canRefund()) {
            throw new DomainException("Payment cannot be refunded from status: " + this.value);
        }
        return REFUNDED;
    }

    public PaymentStatus voidPayment() {
        if (!canVoid()) {
            throw new DomainException("Payment cannot be voided from status: " + this.value);
        }
        return VOID;
    }

    public boolean isAuthorized() {
        return this.value == Status.AUTHORIZED;
    }

    public boolean isCaptured() {
        return this.value == Status.CAPTURED;
    }

    public boolean isVoid() {
        return this.value == Status.VOID;
    }

    public boolean isRefunded() {
        return this.value == Status.REFUNDED;
    }

    public boolean isTerminal() {
        return this.value == Status.VOID || this.value == Status.REFUNDED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.name() : "null";
    }
}
