package com.vtu.api.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NetworkProvider {

    AIRTEL("AIRTEL_22689"),
    MTN("MTN_24207"),
    GLO("GLO_30387"),
    NINE_MOBILE("9MOBILE_69358");

    private final String uniqueCode;
}
