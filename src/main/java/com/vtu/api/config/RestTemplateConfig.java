package com.vtu.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

// Define a RestTemplate bean in a configuration class
@Configuration
public class RestTemplateConfig {

    // Use the RestTemplateBuilder to create and configure the RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(5)) // set the connection timeout
            .setReadTimeout(Duration.ofSeconds(10)) // set the read timeout
            .build(); // build the RestTemplate
    }
}
