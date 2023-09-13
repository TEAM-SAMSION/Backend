package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class NotRegisterUserException extends TodoException{
    public NotRegisterUserException(Error error) {
        super(error);
    }
}
