package com.pawith.userdomain.exception;
import com.pawith.commonmodule.exception.Error;

public class UserDeleteException extends UserException {
    public UserDeleteException(Error error) {
        super(error);
    }
}
