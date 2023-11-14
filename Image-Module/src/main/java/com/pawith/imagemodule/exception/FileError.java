package com.pawith.imagemodule.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;

@Getter
public enum FileError implements Error {
    FILE_EXTENTION_ERROR("잘못된 형식의 파일입니다.", 4000),
    FILE_UPLOAD_ERROR("파일 업로드에 실패했습니다.", 4001),
    FILE_DELETE_ERROR("파일 삭제에 실패했습니다.", 4002);

    private final String message;
    private final int errorCode;

    FileError(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
