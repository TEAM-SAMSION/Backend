package com.pawith.imagemodule.exception;

import com.pawith.commonmodule.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum FileError implements Error {
    FILE_EXTENTION_ERROR("잘못된 형식의 파일입니다.", 4000, HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_ERROR("파일 업로드에 실패했습니다.", 4001, HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_DELETE_ERROR("파일 삭제에 실패했습니다.", 4002, HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final int errorCode;
    private final HttpStatusCode httpStatusCode;
}
