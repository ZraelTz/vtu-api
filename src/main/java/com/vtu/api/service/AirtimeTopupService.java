package com.vtu.api.service;

import com.vtu.api.dto.request.AirtimeTopupRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.AirtimeTopupResponse;
import org.springframework.transaction.annotation.Transactional;

public interface AirtimeTopupService {
    @Transactional
    ApiResponse<AirtimeTopupResponse> topUpAirtime(AirtimeTopupRequest request);
}
