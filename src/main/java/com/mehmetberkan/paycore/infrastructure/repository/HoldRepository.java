package com.mehmetberkan.paycore.infrastructure.repository;

import com.mehmetberkan.paycore.domain.enums.HoldStatus;
import com.mehmetberkan.paycore.domain.model.Hold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldRepository extends JpaRepository<Hold, Long> {
    Optional<Hold> findByPaymentId(Long paymentId);
    List<Hold> findByAccountIdAndStatus(Long accountId, HoldStatus status);
}
