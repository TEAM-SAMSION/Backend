package com.pawith.log.webclient;

import org.springframework.stereotype.Component;

@Component
public class LogDataProcessor {

    private final LogDataSender logDataSender;

    public LogDataProcessor(LogDataSender logDataSender) {
        this.logDataSender = logDataSender;
    }

    public void processLogData(String threadId, Integer executionTime, String methodName, String exceptionMessage) {
        SaveLogRequest saveLogRequest = new SaveLogRequest(threadId,executionTime,methodName,exceptionMessage);
        logDataSender.sendLogData(saveLogRequest);
    }
}