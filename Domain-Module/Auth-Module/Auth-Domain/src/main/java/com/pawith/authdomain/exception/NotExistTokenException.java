package com.pawith.authdomain.exception;

import com.pawith.commonmodule.exception.Error;
import com.pawith.authdomain.jwt.exception.TokenException;

public class NotExistTokenException extends TokenException {
    public NotExistTokenException(Error error) {
        super(error);
    }
}
