package com.pawith.commonmodule.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int errorCode;

    private ErrorResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public static ErrorResponse from(BusinessException exception){
        return new ErrorResponse(exception.getErrorCode());
    }
}
