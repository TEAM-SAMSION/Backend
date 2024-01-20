package com.pawith.alarmmodule.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum AlarmError implements Error {
    DEVICE_TOKEN_NOT_FOUND("FCM 디바이스 토큰이 없습니다.", 5000, HttpStatus.NOT_FOUND),
    FCM_SEND_ERROR("FCM 전송에 실패하였습니다.", 5001, HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final int errorCode;
    private final HttpStatusCode httpStatusCode;
}
