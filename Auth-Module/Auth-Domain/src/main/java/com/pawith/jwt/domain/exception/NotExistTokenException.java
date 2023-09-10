package com.pawith.jwt.domain.exception;

import com.pawith.commonmodule.exception.Error;
import com.pawith.jwt.jwt.exception.TokenException;

public class NotExistTokenException extends TokenException {
    public NotExistTokenException(Error error) {
        super(error);
    }
}
