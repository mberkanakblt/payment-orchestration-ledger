package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.api.dto.response.TopMerchantReportDto;
import com.mehmetberkan.paycore.application.port.in.ReportUseCase;
import com.mehmetberkan.paycore.application.port.out.PaymentPort;
import com.mehmetberkan.paycore.domain.aggregate.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService implements ReportUseCase {

    private final PaymentPort paymentPort;

    @Override
    @Transactional(readOnly = true)
    public List<TopMerchantReportDto> getTopMerchants(String period) {
        Instant now = Instant.now();
        Instant start = calculateStartInstant(period, now);

        List<Payment> payments = paymentPort.findByCreatedAtBetween(start, now);

        Map<Long, List<Payment>> groupedByMerchant = payments.stream()
                .collect(Collectors.groupingBy(Payment::getMerchantId));

        return groupedByMerchant.entrySet().stream()
                .map(entry -> {
                    Long merchantId = entry.getKey();
                    List<Payment> merchantPayments = entry.getValue();

                    BigDecimal totalAmount = merchantPayments.stream()
                            .map(p -> p.getAmount().getAmount())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    long count = merchantPayments.size();

                    return TopMerchantReportDto.builder()
                            .merchantId(merchantId)
                            .totalAmount(totalAmount)
                            .transactionCount(count)
                            .build();
                })
                .sorted(Comparator.comparing(TopMerchantReportDto::getTotalAmount).reversed())
                .toList();
    }

    private Instant calculateStartInstant(String period, Instant now) {
        String normalized = period == null ? "DAY" : period.toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "WEEK" -> now.minus(7, ChronoUnit.DAYS);
            case "MONTH" -> now.minus(30, ChronoUnit.DAYS);
            default -> now.minus(1, ChronoUnit.DAYS);
        };
    }
}

