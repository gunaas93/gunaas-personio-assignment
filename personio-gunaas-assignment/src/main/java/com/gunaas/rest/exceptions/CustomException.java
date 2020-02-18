package com.gunaas.rest.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    ErrorCode errorCode;
    String errorMessage;
    HttpStatus httpStatus;

    public CustomException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage, new Throwable(errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
