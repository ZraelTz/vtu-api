package com.vtu.api.config.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class RetryInterceptor implements ClientHttpRequestInterceptor {
    private final RetryTemplate retryTemplate;

    // Override the intercept method to execute the request with the retry template
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        // Use the retry template to execute the request and return the response
        return retryTemplate.execute(context -> execution.execute(request, body));
    }
}