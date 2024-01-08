package com.vtu.api.service;

import com.vtu.api.dto.request.XpressPayAirtimeTopupRequest;
import com.vtu.api.dto.response.XpressPayApiResponse;

public interface XpressPayService {
    XpressPayApiResponse doAirtimeTopup(XpressPayAirtimeTopupRequest request);
}
