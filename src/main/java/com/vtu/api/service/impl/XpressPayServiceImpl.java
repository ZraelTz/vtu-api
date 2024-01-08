package com.vtu.api.service.impl;

import com.vtu.api.config.XpressPayConfig;
import com.vtu.api.core.exception.HttpRequestException;
import com.vtu.api.dto.request.XpressPayAirtimeTopupRequest;
import com.vtu.api.dto.response.XpressPayApiResponse;
import com.vtu.api.service.HttpService;
import com.vtu.api.service.XpressPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class XpressPayServiceImpl implements XpressPayService {

    private final XpressPayConfig xpressPayConfig;
    private final HttpService<XpressPayApiResponse, Object> httpService;

    @Override
    public XpressPayApiResponse doAirtimeTopup(XpressPayAirtimeTopupRequest request) {
        ResponseEntity<XpressPayApiResponse> xpressPayApiResp = httpService.logger(log)
                .authorization(xpressPayConfig.getPublicKey())
                .endpoint(xpressPayConfig.getBaseUrl() + "/airtime/fulfil")
                .requestBody(request)
                .responseType(XpressPayApiResponse.class)
                .post();

        if (Objects.isNull(xpressPayApiResp)) {
            throw new HttpRequestException();
        }

        Optional<XpressPayApiResponse> xpressPayApiRespBody = Optional.ofNullable(xpressPayApiResp.getBody());
        if (xpressPayApiRespBody.isEmpty()) {
            throw new HttpRequestException();
        }

        HttpStatusCode httpStatusCode = xpressPayApiResp.getStatusCode();
        if (!httpStatusCode.is2xxSuccessful()) {
            throw new HttpRequestException(
                    xpressPayApiRespBody.get().getResponseMessage(), HttpStatus.valueOf(httpStatusCode.value()));
        }

        return xpressPayApiRespBody.get();
    }
}
