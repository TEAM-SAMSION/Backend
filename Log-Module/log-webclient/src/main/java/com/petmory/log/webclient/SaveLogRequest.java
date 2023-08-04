package com.petmory.log.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveLogRequest {
    private String logId;
    private Integer executeTime;
    private String methodName;
    private String exceptionMessage;
}
