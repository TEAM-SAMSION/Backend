package com.pawith.usermodule.application.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException(Error error) {
        super(error);
    }
}
