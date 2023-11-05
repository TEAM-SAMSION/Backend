package com.pawith.commonmodule.exception;

import lombok.Getter;

@Getter
public enum Error {
    // Security
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1000),
    EXPIRED_TOKEN("만료된 토큰입니다.", 1001),
    NOT_EXIST_TOKEN("토큰이 존재하지 않습니다.", 1002),
    INVALID_AUTHORIZATION_TYPE("유효하지 않은 Authorization Type 입니다.", 1003),
    EMPTY_AUTHORIZATION_HEADER("Authorization Header가 비어있습니다.", 1004),
    OAUTH_FAIL("OAuth 인증에 실패하였습니다.", 1005),

    // User
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 2000),
    USER_AUTHORITY_NOT_FOUND("사용자 권한을 찾을 수 없습니다.", 2001),
    ACCOUNT_ALREADY_EXIST("이미 가입한 계정이 있습니다", 2002),


    // Todo
    TODO_TEAM_NOT_FOUND("패밀리를 찾을 수 없습니다.", 3000),
    NOT_REGISTER_USER("패밀리에 등록되지 않은 사용자입니다.", 3001),
    REGISTER_NOT_FOUND("등록된 패밀리를 찾을 수 없습니다.", 3002),
    ALREADY_REGISTER_TODO_TEAM("이미 등록된 패밀리입니다.", 3003),
    TODO_NOT_FOUND("Todo를 찾을 수 없습니다.", 3004),
    WRONG_SCHEDULED_DATE("예정일이 잘못되었습니다.", 3005),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.", 3006),
    CANNOT_PRESIDENT_UNREGISTRABLE("팀장 탈퇴시 탈퇴 후 팀장이 1명 이상 있어야 합니다.", 3007),

    // Image
    FILE_EXTENTION_ERROR("잘못된 형식의 파일입니다.", 4000),
    FILE_UPLOAD_ERROR("파일 업로드에 실패했습니다.", 4001),
    FILE_DELETE_ERROR("파일 삭제에 실패했습니다.", 4002),
    ;

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
