package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class AssignNotFoundException extends TodoException{
    public AssignNotFoundException(Error error) {
        super(error);
    }
}
