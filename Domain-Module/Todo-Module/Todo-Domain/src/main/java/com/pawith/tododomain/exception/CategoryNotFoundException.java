package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class CategoryNotFoundException extends TodoException{
    public CategoryNotFoundException(Error error) {
        super(error);
    }
}
