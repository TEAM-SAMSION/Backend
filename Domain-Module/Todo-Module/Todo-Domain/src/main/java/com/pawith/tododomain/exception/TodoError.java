package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum TodoError implements Error {
    TODO_TEAM_NOT_FOUND("패밀리를 찾을 수 없습니다.", 3000, HttpStatus.NOT_FOUND),
    NOT_REGISTER_USER("패밀리에 등록되지 않은 사용자입니다.", 3001, HttpStatus.BAD_REQUEST),
    REGISTER_NOT_FOUND("등록된 패밀리를 찾을 수 없습니다.", 3002, HttpStatus.NOT_FOUND),
    ALREADY_REGISTER_TODO_TEAM("이미 등록된 패밀리입니다.", 3003, HttpStatus.BAD_REQUEST),
    TODO_NOT_FOUND("Todo를 찾을 수 없습니다.", 3004, HttpStatus.NOT_FOUND),
    WRONG_SCHEDULED_DATE("예정일이 잘못되었습니다.", 3005, HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.", 3006, HttpStatus.NOT_FOUND),
    CANNOT_PRESIDENT_UNREGISTRABLE("팀장 탈퇴시 탈퇴 후 팀장이 1명 이상 있어야 합니다.", 3007, HttpStatus.BAD_REQUEST),
    CANNOT_CHANGE_AUTHORITY("권한을 변경할 수 없습니다", 3008, HttpStatus.BAD_REQUEST),
    ASSIGN_NOT_FOUND("할당된 Todo를 찾을 수 없습니다.", 3009, HttpStatus.NOT_FOUND),
    TODO_MODIFICATION_NOT_ALLOWED("Todo 수정 및 삭제가 허용되지 않습니다.", 3010, HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final int errorCode;
    private final HttpStatusCode httpStatusCode;
}
