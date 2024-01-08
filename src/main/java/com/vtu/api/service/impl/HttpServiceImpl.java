package com.vtu.api.service.impl;

import com.vtu.api.service.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@Service
public class HttpServiceImpl<T, R> implements HttpService<T, R> {
    private R requestBody;
    private String endpoint;
    private Class<T> responseType;
    private HttpHeaders headers;
    private Logger logger;
    private String authorization;

    private final RestTemplate restTemplate;

    @Override
    public HttpService<T, R> requestBody(R requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override
    public HttpService<T, R> endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public HttpService<T, R> responseType(Class<T> responseType) {
        this.responseType = responseType;
        return this;
    }

    @Override
    public HttpService<T, R> headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public HttpService<T, R> logger(Logger logger) {
        this.logger = logger;
        return this;
    }

    @Override
    public HttpService<T, R> authorization(String authorization) {
        this.authorization = authorization;
        return this;
    }

    @Override
    public ResponseEntity<T> post(Object... uriVariables) {
        try {
            HttpHeaders defaultPostHeaders = getDefaultPostHeaders();
            HttpEntity<R> httpEntity = new HttpEntity<>(requestBody, defaultPostHeaders);
            log.info(httpEntity.toString());
            return restTemplate.postForEntity(
                    endpoint, httpEntity, responseType, uriVariables);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAs(responseType), e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<T> post(UriComponents uriComponents, Object... uriVariables) {
        try {
            HttpEntity<R> httpEntity = new HttpEntity<>(requestBody, getDefaultPostHeaders());
            log.info(httpEntity.toString());
            return restTemplate.postForEntity(
                    uriComponents.toUriString(), httpEntity, responseType, uriVariables);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAs(responseType), e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<T> get(Object... uriVariables) {
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<>(getHeaders());
            log.info(httpEntity.toString());
            return restTemplate.exchange(
                    endpoint, HttpMethod.GET, httpEntity, responseType, uriVariables);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAs(responseType), e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<T> get(UriComponents uriComponents, Object... uriVariables) {
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<>(getHeaders());
            log.info(httpEntity.toString());
            return restTemplate.exchange(
                    uriComponents.toUriString(), HttpMethod.GET, httpEntity, responseType, uriVariables);

        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAs(responseType), e.getStatusCode());
        }
    }

    private HttpHeaders getHeaders() {
        if (Objects.isNull(headers)) {
            headers = new HttpHeaders();
        }

        if (StringUtils.isNotBlank(authorization)) {
            if (!headers.containsKey(AUTHORIZATION)) {
                headers.add(AUTHORIZATION, authorization);
            }
        }

        return headers;
    }

    private HttpHeaders getDefaultPostHeaders() {
        HttpHeaders httpHeaders = getHeaders();
        if (!headers.containsKey(CONTENT_TYPE)) {
            httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        }

        if (!headers.containsKey(ACCEPT)) {
            httpHeaders.add(ACCEPT, APPLICATION_JSON_VALUE);
        }

        return httpHeaders;
    }

}
