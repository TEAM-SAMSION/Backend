package com.pawith.authdomain.jwt.exception;

import com.pawith.commonmodule.exception.Error;

public class InvalidTokenException extends TokenException{
    public InvalidTokenException(Error error) {
        super(error);
    }
}
