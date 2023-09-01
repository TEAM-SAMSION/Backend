package com.pawith.usermodule.domain.exception;

import com.pawith.commonmodule.exception.Error;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(Error error) { super(error); }
}
