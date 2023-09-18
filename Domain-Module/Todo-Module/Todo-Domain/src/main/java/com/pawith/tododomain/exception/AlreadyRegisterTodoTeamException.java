package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;

public class AlreadyRegisterTodoTeamException extends TodoException{
    public AlreadyRegisterTodoTeamException(Error error) {
        super(error);
    }
}
