package com.pawith.imageapplication.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class FileDeleteException extends BusinessException {
    public FileDeleteException(Error error) {
        super(error);
    }
}

