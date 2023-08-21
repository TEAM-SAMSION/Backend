package com.pawith.usermodule.domain.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class UserException extends BusinessException {
    public UserException(Error error) {
        super(error);
    }
}
