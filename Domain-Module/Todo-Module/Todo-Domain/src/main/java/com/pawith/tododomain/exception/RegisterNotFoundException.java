package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class RegisterNotFoundException extends TodoException{
    public RegisterNotFoundException(Error error) {
        super(error);
    }
}
