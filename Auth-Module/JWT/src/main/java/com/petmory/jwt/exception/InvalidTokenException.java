package com.petmory.jwt.exception;

import com.petmory.commonmodule.exception.Error;

public class InvalidTokenException extends TokenException{
    public InvalidTokenException(Error error) {
        super(error);
    }
}
