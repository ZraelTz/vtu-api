package com.vtu.api.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class XpressPayAirtimeTopupRequest extends XpressPayApiRequest {

    private String uniqueCode;
    private TopupDetails topupDetails;

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class TopupDetails {
        private String phoneNumber;
        private Integer amount;
    }
}
