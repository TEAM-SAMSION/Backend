package com.pawith.commonmodule.exception;

import org.springframework.http.HttpStatusCode;


public interface Error {
    String getMessage();
    int getErrorCode();

    HttpStatusCode getHttpStatusCode();
}
