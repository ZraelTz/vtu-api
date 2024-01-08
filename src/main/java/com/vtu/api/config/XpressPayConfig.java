package com.vtu.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xpresspay", ignoreUnknownFields = false)
@Getter
@Setter
@NoArgsConstructor
public class XpressPayConfig {

    private String baseUrl;
    private String publicKey;

    public String getPublicKey(){
        return "Bearer " + publicKey;
    }
}
