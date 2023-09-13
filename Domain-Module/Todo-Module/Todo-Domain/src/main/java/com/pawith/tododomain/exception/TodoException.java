package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class TodoException extends BusinessException {
    public TodoException(Error error) {
        super(error);
    }
}
