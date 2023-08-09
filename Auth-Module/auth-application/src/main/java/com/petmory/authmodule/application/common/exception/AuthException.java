package com.petmory.authmodule.application.common.exception;

import com.petmory.commonmodule.exception.BusinessException;
import com.petmory.commonmodule.exception.Error;

public class AuthException extends BusinessException {
    public AuthException(Error error) {
        super(error);
    }
}
