package com.pawith.todoapplication.exception;
import com.pawith.commonmodule.exception.Error;

public class WrongScheduledDateException extends TodoException{
    public WrongScheduledDateException(Error error) { super(error);}
}
