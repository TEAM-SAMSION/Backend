package com.pawith.alarmmodule.exception;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.Error;

public class AlarmException extends BusinessException {
    public AlarmException(Error error) {
        super(error);
    }
}
