package com.petmory.jwt.exception;

import com.petmory.commonmodule.exception.Error;

public class NotExistTokenException extends TokenException{
    public NotExistTokenException(Error error) {
        super(error);
    }
}
