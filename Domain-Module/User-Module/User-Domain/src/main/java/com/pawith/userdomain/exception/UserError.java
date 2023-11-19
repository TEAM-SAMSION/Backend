package com.pawith.userdomain.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;

@Getter
public enum UserError implements Error {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 2000),
    USER_AUTHORITY_NOT_FOUND("사용자 권한을 찾을 수 없습니다.", 2001),
    ACCOUNT_ALREADY_EXIST("이미 가입한 계정이 있습니다", 2002),
    CANNOT_DELETE_USER("사용자를 삭제할 수 없습니다.", 2003);

    private final String message;
    private final int errorCode;

    UserError(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
