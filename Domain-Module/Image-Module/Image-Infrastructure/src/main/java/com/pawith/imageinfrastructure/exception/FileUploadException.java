package com.pawith.imageinfrastructure.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class FileUploadException extends BusinessException {
    public FileUploadException(Error error) {
        super(error);
    }
}

