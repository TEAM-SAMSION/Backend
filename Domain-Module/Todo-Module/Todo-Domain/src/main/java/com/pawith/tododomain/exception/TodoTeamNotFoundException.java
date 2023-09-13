package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class TodoTeamNotFoundException extends TodoException{
    public TodoTeamNotFoundException(Error error) {
        super(error);
    }
}
