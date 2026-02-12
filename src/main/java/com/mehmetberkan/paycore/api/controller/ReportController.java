package com.mehmetberkan.paycore.api.controller;

import com.mehmetberkan.paycore.api.dto.response.TopMerchantReportDto;
import com.mehmetberkan.paycore.application.port.in.ReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportController {

    private final ReportUseCase reportUseCase;

    @GetMapping("/merchants/top")
    public ResponseEntity<List<TopMerchantReportDto>> getTopMerchants(
            @RequestParam(defaultValue = "DAY") String period
    ) {
        List<TopMerchantReportDto> result = reportUseCase.getTopMerchants(period);
        return ResponseEntity.ok(result);
    }
}

