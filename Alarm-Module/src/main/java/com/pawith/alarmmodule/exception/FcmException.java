package com.pawith.alarmmodule.exception;

import com.pawith.commonmodule.exception.Error;

public class FcmException extends AlarmException{
    public FcmException(Error error) {
        super(error);
    }
}
