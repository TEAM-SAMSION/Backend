package com.pawith.commonmodule.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final Error error;

    public BusinessException(Error error){
        this.error = error;
    }

    public String getMessage(){
        return error.getMessage();
    }

    public int getErrorCode(){
        return error.getErrorCode();
    }
}
