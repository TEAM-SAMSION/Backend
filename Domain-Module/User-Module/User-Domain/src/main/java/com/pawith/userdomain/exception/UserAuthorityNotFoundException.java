package com.pawith.userdomain.exception;

import com.pawith.commonmodule.exception.Error;

public class UserAuthorityNotFoundException extends UserException{
    public UserAuthorityNotFoundException(Error error) {
        super(error);
    }
}
