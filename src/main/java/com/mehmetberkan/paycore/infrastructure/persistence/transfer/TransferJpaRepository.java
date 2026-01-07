package com.mehmetberkan.paycore.infrastructure.persistence.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferJpaRepository extends JpaRepository<TransferJpaEntity, Long> {
    Optional<TransferJpaEntity> findByIdempotencyKey(String idempotencyKey);
}
