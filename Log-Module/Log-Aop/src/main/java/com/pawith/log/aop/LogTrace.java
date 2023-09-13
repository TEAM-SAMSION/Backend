package com.pawith.log.aop;

import com.pawith.commonmodule.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogTrace {

    private static final String TRACE_ID = "TraceId";
    private static final Logger logger = LoggerFactory.getLogger(LogTrace.class);

    public TraceStatus start(String className, String method) {
        String id = createTraceId();
        MDC.put(TRACE_ID, id);
        long startTime = System.currentTimeMillis();
        logger.info("[" + id + "] " + method + " ==== start");
        return new TraceStatus(id, startTime, className, method);
    }

    public Integer end(TraceStatus traceStatus) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - traceStatus.getStartTime();
        if (executionTime > 1000) {
            logger.warn("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        } else {
            logger.info("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + traceStatus.getMethodName() + " ==== execute time = " + executionTime + "ms");
        }
        return (int)executionTime;
    }

    public void apiException(BusinessException e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + traceStatus.getMethodName() + " ==== API EXCEPTION! [" + e.getError().getErrorCode() + "] " + e.getError().getMessage());
        removeMdcContext();
    }

    public void exception(Exception e, TraceStatus traceStatus) {
        logger.error("[" + traceStatus.getThreadId() + "] " + traceStatus.getClassName() + traceStatus.getMethodName() + " ==== INTERNAL ERROR! " + e.getMessage());
        e.printStackTrace();
        removeMdcContext();
    }


    private String createTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void removeMdcContext() {
        MDC.remove(TRACE_ID);
    }
}