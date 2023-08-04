package com.petmory.log.aop;

import com.petmory.commonmodule.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogTrace {

    private ThreadLocal<String> threadId = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(LogTrace.class);

    public TraceStatus start(String method) {
        syncTrace();
        String id = threadId.get();
        long startTime = System.currentTimeMillis();
        logger.info("[" + id + "] " + method + " ==== start");
        return new TraceStatus(id, startTime, method);
    }

    public Integer end(TraceStatus traceStatus) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - traceStatus.getStartTime();
        if (executionTime > 1000) {
            logger.warn("[" + traceStatus.getThreadId() + "] " + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        } else {
            logger.info("[" + traceStatus.getThreadId() + "] " + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        }
        removeThreadLocal();
        return (int)executionTime;
    }

    public void apiException(BusinessException e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getMethodName() + " ==== API EXCEPTION! [" + e.getError().getErrorCode() + "] " + e.getError().getMessage());
        removeThreadLocal();
    }

    public void exception(Exception e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getMethodName() + " ==== INTERNAL ERROR! ");
        e.printStackTrace();
        removeThreadLocal();
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

    private void removeThreadLocal() {
        threadId.remove();
    }
}