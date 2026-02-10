package com.mehmetberkan.paycore.application.port.out;

import com.mehmetberkan.paycore.domain.aggregate.Payment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PaymentPort {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
    List<Payment> findByCreatedAtBetween(Instant start, Instant end);
}
