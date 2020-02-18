package com.gunaas.rest.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ApiError apiError = new ApiError(ErrorCode.UNCAUGHT_EXCEPTION, HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity(apiError, apiError.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity(apiError, apiError.getStatus());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(ErrorCode.INVALID_PARAMS, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ErrorCode.UNCAUGHT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Error Occurred");
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(ErrorCode.INVALID_PARAMS, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getCause().getMessage());
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiError> handleCustomException(CustomException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getErrorCode(), ex.getHttpStatus(), ex.getLocalizedMessage(), ex.getErrorMessage());
        return new ResponseEntity(apiError, apiError.getStatus());
    }
}
