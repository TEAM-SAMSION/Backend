package com.petmory.commonmodule.exception;

import lombok.Getter;

@Getter
public enum Error {
    // Security
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1001),
    EXPIRED_TOKEN("만료된 토큰입니다.", 1002),
    NOT_EXIST_TOKEN("토큰이 존재하지 않습니다.", 1003),
    INVALID_AUTHORIZATION_TYPE("유효하지 않은 Authorization Type 입니다.", 1004),
    EMPTY_AUTHORIZATION_HEADER("Authorization Header가 비어있습니다.", 1005),
    ;

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
