package com.pawith.log.aop;

import com.pawith.commonmodule.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogTrace {

    private ThreadLocal<String> threadId = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(LogTrace.class);

    public TraceStatus start(String fullClassName, String method) {
        syncTrace();
        String id = threadId.get();
        long startTime = System.currentTimeMillis();
        logger.info("[" + id + "] " + method + " ==== start");
        int lastDotIndex = fullClassName.lastIndexOf(".");
        String className = fullClassName.substring(lastDotIndex + 1);
        return new TraceStatus(id, startTime, className, method);
    }

    public Integer end(TraceStatus traceStatus) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - traceStatus.getStartTime();
        if (executionTime > 1000) {
            logger.warn("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + " " + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        } else {
            logger.info("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + " " + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        }
        return (int)executionTime;
    }

    public void apiException(BusinessException e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + " " +  traceStatus.getMethodName() + " [" + e.getError().getErrorCode() + "] " + e.getError().getMessage());
    }

    public void exception(Exception e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName()  + " " +  traceStatus.getMethodName() + " " + e.getMessage());
        e.printStackTrace();
    }

    private void syncTrace() {
        String id = threadId.get();
        if (id == null) {
            threadId.set(createThreadId());
        }
    }

    private String createThreadId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}