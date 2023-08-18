package com.pawith.jwt.exception;

import com.pawith.commonmodule.exception.Error;

public class ExpiredTokenException extends TokenException {
    public ExpiredTokenException(Error error) {
        super(error);
    }
}
