package com.pawith.tododomain.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;

@Getter
public enum TodoError implements Error {
    TODO_TEAM_NOT_FOUND("패밀리를 찾을 수 없습니다.", 3000),
    NOT_REGISTER_USER("패밀리에 등록되지 않은 사용자입니다.", 3001),
    REGISTER_NOT_FOUND("등록된 패밀리를 찾을 수 없습니다.", 3002),
    ALREADY_REGISTER_TODO_TEAM("이미 등록된 패밀리입니다.", 3003),
    TODO_NOT_FOUND("Todo를 찾을 수 없습니다.", 3004),
    WRONG_SCHEDULED_DATE("예정일이 잘못되었습니다.", 3005),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.", 3006),
    CANNOT_PRESIDENT_UNREGISTRABLE("팀장 탈퇴시 탈퇴 후 팀장이 1명 이상 있어야 합니다.", 3007),
    CANNOT_CHANGE_AUTHORITY("권한을 변경할 수 없습니다", 3008),
    ASSIGN_NOT_FOUND("할당된 Todo를 찾을 수 없습니다.", 3009)
    ;

    private final String message;
    private final int errorCode;

    TodoError(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
