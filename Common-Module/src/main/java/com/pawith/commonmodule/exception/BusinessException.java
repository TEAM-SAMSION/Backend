package com.pawith.commonmodule.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

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

    public HttpStatusCode getHttpStatusCode(){
        return error.getHttpStatusCode();
    }
}
