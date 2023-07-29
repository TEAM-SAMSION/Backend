package com.petmory.log.webclient;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class SaveLogRequest {
    private String logId;
    private Integer executionTime;
    private String methodName;
    private String exceptionMessage;
    private boolean isSuccess;
}
