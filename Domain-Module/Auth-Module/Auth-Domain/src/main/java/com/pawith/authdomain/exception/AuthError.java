package com.pawith.authdomain.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthError implements Error {

    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1000, HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("만료된 토큰입니다.", 1001, HttpStatus.BAD_REQUEST),
    NOT_EXIST_TOKEN("토큰이 존재하지 않습니다.", 1002, HttpStatus.BAD_REQUEST),
    INVALID_AUTHORIZATION_TYPE("유효하지 않은 Authorization Type 입니다.", 1003, HttpStatus.BAD_REQUEST),
    EMPTY_AUTHORIZATION_HEADER("Authorization Header가 비어있습니다.", 1004, HttpStatus.BAD_REQUEST),
    OAUTH_FAIL("OAuth 인증에 실패하였습니다.", 1005, HttpStatus.BAD_REQUEST),
    INVALID_OAUTH_REQUEST("다른 소셜로그인으로 가입된 계정이 존재합니다.", 2002, HttpStatus.BAD_REQUEST), // 오류 코드 변경 요청하기
    ;

    private final String message;
    private final int errorCode;
    private final HttpStatusCode httpStatusCode;
}
