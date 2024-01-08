package com.vtu.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vtu.api.dto.request.AirtimeTopupRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtimeTopupResponse extends AirtimeTopupRequest {
}
