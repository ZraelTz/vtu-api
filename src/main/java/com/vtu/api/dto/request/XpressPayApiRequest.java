package com.vtu.api.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class XpressPayApiRequest {

    private final String requestId = "vtu: " + UUID.randomUUID()
            .toString()
            .replaceAll("-", "")
            .concat("@" + LocalDateTime.now(ZoneId.systemDefault()));
}
