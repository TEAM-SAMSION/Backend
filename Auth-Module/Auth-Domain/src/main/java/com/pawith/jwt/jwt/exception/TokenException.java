package com.pawith.jwt.jwt.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class TokenException extends BusinessException {
    public TokenException(Error error) {
        super(error);
    }
}
