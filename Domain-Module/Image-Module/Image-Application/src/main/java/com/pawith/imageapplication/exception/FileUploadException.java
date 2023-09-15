package com.pawith.imageapplication.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class FileUploadException extends BusinessException {
    public FileUploadException(Error error) {
        super(error);
    }
}

