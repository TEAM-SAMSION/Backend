package com.pawith.usermodule.domain.exception;

import com.pawith.commonmodule.exception.Error;

public class UserAuthorityNotFoundException extends UserException{
    public UserAuthorityNotFoundException(Error error) {
        super(error);
    }
}
