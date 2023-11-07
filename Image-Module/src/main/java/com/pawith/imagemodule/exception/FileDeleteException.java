package com.pawith.imagemodule.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class FileDeleteException extends BusinessException {
    public FileDeleteException(Error error) {
        super(error);
    }
}

