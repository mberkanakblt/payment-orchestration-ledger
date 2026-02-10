package com.mehmetberkan.paycore.infrastructure.persistence.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
    Optional<PaymentJpaEntity> findByIdempotencyKey(String idempotencyKey);
    List<PaymentJpaEntity> findByCreatedAtBetween(Instant start, Instant end);
}
