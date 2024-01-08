package com.vtu.api.service.impl;

import com.vtu.api.dto.request.AirtimeTopupRequest;
import com.vtu.api.dto.request.XpressPayAirtimeTopupRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.AirtimeTopupResponse;
import com.vtu.api.dto.response.XpressPayApiResponse;
import com.vtu.api.mapper.AirtimeTopupMapper;
import com.vtu.api.model.entity.AirtimeTopup;
import com.vtu.api.repository.AirtimeTopupRepository;
import com.vtu.api.service.AuthenticationService;
import com.vtu.api.service.AirtimeTopupService;
import com.vtu.api.service.XpressPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirtimeTopupServiceImpl implements AirtimeTopupService {
    private final AirtimeTopupRepository airtimeTopupRepository;
    private final XpressPayService xpressPayService;
    private final AirtimeTopupMapper airtimeTopupMapper;
    private final AuthenticationService authService;

    @Override
    public ApiResponse<AirtimeTopupResponse> topUpAirtime(AirtimeTopupRequest request) {
        XpressPayAirtimeTopupRequest apiRequest = airtimeTopupMapper.createApiRequest(request);
        XpressPayApiResponse xpressPayApiResponse = xpressPayService.doAirtimeTopup(apiRequest);

        AirtimeTopup airtimeTopup = airtimeTopupMapper.createAirtimeTopup(request);
        airtimeTopup.setReferenceId(xpressPayApiResponse.getReferenceId());
        airtimeTopup.setRequestId(xpressPayApiResponse.getRequestId());
        airtimeTopup.setUser(authService.getAuthUser());
        airtimeTopupRepository.save(airtimeTopup);

        return ApiResponse.<AirtimeTopupResponse>builder()
                .message(xpressPayApiResponse.getResponseMessage())
                .data(airtimeTopupMapper.createTopupResponse(request))
                .build();
    }


}
