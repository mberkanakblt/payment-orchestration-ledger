package com.mehmetberkan.paycore.application.port.in;

import com.mehmetberkan.paycore.api.dto.response.TopMerchantReportDto;

import java.util.List;

public interface ReportUseCase {
    List<TopMerchantReportDto> getTopMerchants(String period);
}

