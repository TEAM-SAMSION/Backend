package com.pawith.commonmodule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(BusinessException e) {
        final ErrorResponse errorResponse = ErrorResponse.from(e);
        return new ResponseEntity<>(errorResponse, e.getHttpStatusCode());
    }
}
