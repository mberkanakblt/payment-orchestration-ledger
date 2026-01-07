package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.domain.enums.HoldStatus;
import com.mehmetberkan.paycore.domain.model.Hold;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import com.mehmetberkan.paycore.infrastructure.repository.HoldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoldService {

    private final HoldRepository holdRepository;


    @Transactional
    public Hold createHold(Long accountId, Long paymentId, Money amount) {
        Hold hold = Hold.builder()
                .accountId(accountId)
                .paymentId(paymentId)
                .amount(amount)
                .status(HoldStatus.ACTIVE)
                .createdAt(Instant.now())
                .build();
        return holdRepository.save(hold);
    }


    @Transactional
    public void releaseHold(Long paymentId) {
        Hold hold = holdRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Hold not found: " + paymentId));
        
        hold.setStatus(HoldStatus.RELEASED);
        hold.setReleasedAt(Instant.now());
        holdRepository.save(hold);
    }


    @Transactional
    public void captureHold(Long paymentId) {
        Hold hold = holdRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Hold not found: " + paymentId));
        
        hold.setStatus(HoldStatus.CAPTURED);
        holdRepository.save(hold);
    }


    public Optional<Hold> findByPaymentId(Long paymentId) {
        return holdRepository.findByPaymentId(paymentId);
    }
}

