package com.pawith.usermodule.exception;

import com.pawith.commonmodule.exception.Error;

public class UserAuthorityNotFoundException extends UserException{
    public UserAuthorityNotFoundException(Error error) {
        super(error);
    }
}
