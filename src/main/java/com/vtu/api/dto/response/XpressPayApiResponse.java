package com.vtu.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class XpressPayApiResponse {

    private String referenceId;
    private String requestId;
    private String responseCode;
    private String responseMessage;
    private Map<String, Object> data;
}
