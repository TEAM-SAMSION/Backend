package com.petmory.log.webclient;

import org.springframework.stereotype.Component;

@Component
public class LogDataProcessor {

    private final LogDataSender logDataSender;

    public LogDataProcessor(LogDataSender logDataSender) {
        this.logDataSender = logDataSender;
    }

    public void processLogData(String threadId, Integer executionTime, String methodName, String exceptionMessage, boolean success) {
        SaveLogRequest saveLogRequest = new SaveLogRequest(threadId,executionTime,methodName,exceptionMessage,success);
        logDataSender.sendLogData(saveLogRequest);
    }
}