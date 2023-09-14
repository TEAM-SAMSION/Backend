package com.pawith.authmodule.exception;

import com.pawith.commonmodule.exception.Error;

public class InvalidAuthorizationTypeException extends AuthException {
    public InvalidAuthorizationTypeException(Error error) {
        super(error);
    }
}
