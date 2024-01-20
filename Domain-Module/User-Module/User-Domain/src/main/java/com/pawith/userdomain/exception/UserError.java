package com.pawith.userdomain.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum UserError implements Error {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 2000, HttpStatus.NOT_FOUND),
    USER_AUTHORITY_NOT_FOUND("사용자 권한을 찾을 수 없습니다.", 2001, HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_EXIST("이미 가입한 계정이 있습니다", 2002, HttpStatus.BAD_REQUEST);

    private final String message;
    private final int errorCode;
    private final HttpStatusCode httpStatusCode;
}
