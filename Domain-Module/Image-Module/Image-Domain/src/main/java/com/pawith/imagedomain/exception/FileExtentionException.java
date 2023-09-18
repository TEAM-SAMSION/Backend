package com.pawith.imageapplication.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class FileExtentionException extends BusinessException {
    public FileExtentionException(Error error) {
        super(error);
    }
}

