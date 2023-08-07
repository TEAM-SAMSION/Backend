package com.petmory.jwt.exception;

import com.petmory.commonmodule.exception.BusinessException;
import com.petmory.commonmodule.exception.Error;

public class TokenException extends BusinessException {
    public TokenException(Error error) {
        super(error);
    }
}
