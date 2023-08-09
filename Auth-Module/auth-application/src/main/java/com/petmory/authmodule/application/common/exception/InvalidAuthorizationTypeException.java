package com.petmory.authmodule.application.common.exception;

import com.petmory.commonmodule.exception.Error;

public class InvalidAuthorizationTypeException extends AuthException {
    public InvalidAuthorizationTypeException(Error error) {
        super(error);
    }
}
