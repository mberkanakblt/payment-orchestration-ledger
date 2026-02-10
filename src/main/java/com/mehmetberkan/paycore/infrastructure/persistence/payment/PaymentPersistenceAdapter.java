package com.mehmetberkan.paycore.infrastructure.persistence.payment;

import com.mehmetberkan.paycore.application.port.out.PaymentPort;
import com.mehmetberkan.paycore.domain.aggregate.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper mapper;

    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = mapper.toEntity(payment);
        PaymentJpaEntity savedEntity = paymentJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByIdempotencyKey(String idempotencyKey) {
        return paymentJpaRepository.findByIdempotencyKey(idempotencyKey)
                .map(mapper::toDomain);
    }

    @Override
    public List<Payment> findByCreatedAtBetween(Instant start, Instant end) {
        return paymentJpaRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
