package com.pawith.usermodule.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class AccountAlreadyExistException extends BusinessException {
    public AccountAlreadyExistException(Error error) {
        super(error);
    }
}
