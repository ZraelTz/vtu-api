package com.vtu.api.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpRequestException extends RuntimeException {
    private static final String GENERIC_ERROR_MESSAGE
            = "An error occurred while making the request, please try again";
    protected String message = GENERIC_ERROR_MESSAGE;
    protected HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public HttpRequestException(String message) {
        super(message);
        this.message = message;
    }

    public HttpRequestException() {
        super(GENERIC_ERROR_MESSAGE);
    }

    public HttpRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
