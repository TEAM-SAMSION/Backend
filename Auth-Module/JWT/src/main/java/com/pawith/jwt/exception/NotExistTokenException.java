package com.pawith.jwt.exception;

import com.pawith.commonmodule.exception.Error;

public class NotExistTokenException extends TokenException{
    public NotExistTokenException(Error error) {
        super(error);
    }
}
