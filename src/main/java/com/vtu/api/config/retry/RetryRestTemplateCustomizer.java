package com.vtu.api.config.retry;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.*;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RetryRestTemplateCustomizer implements RestTemplateCustomizer {
    // Define a customizer class that implements RestTemplateCustomizer and uses RetryTemplate
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Set the fixed backoff policy with 2 seconds interval
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000L);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        // Create a map of retryable exceptions
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(ResourceAccessException.class, true); // retry on connection errors
        retryableExceptions.put(HttpServerErrorException.class, true); // retry on 5xx errors

        // Set the simple retry policy with 3 max attempts and retryable status codes
        // Pass the map to the SimpleRetryPolicy constructor
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3, retryableExceptions); // 3 max attempts
        retryPolicy.setMaxAttempts(3);

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(new DefaultListenerSupport());

        return retryTemplate;
    }

    // Override the customize method to add the retry logic to the RestTemplate
    @Override
    public void customize(RestTemplate restTemplate) {
        // Get the default request factory of the RestTemplate
        ClientHttpRequestFactory requestFactory = restTemplate.getRequestFactory();

        // Wrap the request factory with a BufferingClientHttpRequestFactory,
        // so the response can be read multiple times
        BufferingClientHttpRequestFactory bufferingRequestFactory
                = new BufferingClientHttpRequestFactory(requestFactory);

        // Wrap the buffering request factory with a InterceptingClientHttpRequestFactory,
        // so we can add an interceptor that uses the retry template
        InterceptingClientHttpRequestFactory interceptingRequestFactory
                = new InterceptingClientHttpRequestFactory(
                        bufferingRequestFactory, Collections.singletonList(
                                new RetryInterceptor(retryTemplate())));

        // Set the intercepting request factory to the RestTemplate
        restTemplate.setRequestFactory(interceptingRequestFactory);
    }

}
