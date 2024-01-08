package com.vtu.api.service;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;

public interface HttpService<T, R> {

    HttpService<T, R> requestBody(R requestBody);
    HttpService<T, R> endpoint(String endpoint);
    HttpService<T, R> responseType(Class<T> responseType);
    HttpService<T, R> headers(HttpHeaders headers);
    HttpService<T, R> logger(Logger logger);
    HttpService<T, R> authorization(String authorization);

    ResponseEntity<T> post(Object... uriVariables);
    ResponseEntity<T> post(UriComponents uriComponents, Object... uriVariables);
    ResponseEntity<T> get(Object... uriVariables);
    ResponseEntity<T> get(UriComponents uriComponents, Object... uriVariables);

}
