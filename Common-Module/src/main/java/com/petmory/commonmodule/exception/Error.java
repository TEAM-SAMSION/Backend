package com.petmory.commonmodule.exception;

import lombok.Getter;

@Getter
public enum Error {
    ;

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
