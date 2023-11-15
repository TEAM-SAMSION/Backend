package com.pawith.alarmmodule.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AlarmError implements Error {
    DEVICE_TOKEN_NOT_FOUND("FCM 디바이스 토큰이 없습니다.", 5000),
    FCM_SEND_ERROR("FCM 전송에 실패하였습니다.", 5001),;

    private String message;
    private Integer errorCode;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
