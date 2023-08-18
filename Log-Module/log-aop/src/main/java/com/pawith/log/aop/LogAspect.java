package com.pawith.log.aop;

import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.log.webclient.LogDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private final LogTrace logTrace;
    private final LogDataProcessor logDataProcessor;


    public LogAspect(LogTrace logTrace, LogDataProcessor logDataProcessor) {
        this.logTrace = logTrace;
        this.logDataProcessor = logDataProcessor;
    }

    @Around("com.pawith.log.aop.Pointcuts.allService()")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    @Around("com.pawith.log.aop.Pointcuts.allController()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("controllerLog: {}", joinPoint.getSignature().getName());
        return getObject(joinPoint);
    }

    private Object getObject(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.start(joinPoint.getSignature().getName());
            Object result = joinPoint.proceed();
            Integer executionTime = logTrace.end(traceStatus);
            logDataProcessor.processLogData(traceStatus.getThreadId(), executionTime, traceStatus.getMethodName(), null);
            return result;
        } catch (BusinessException e) {
            if (traceStatus != null) {
                logTrace.apiException(e, traceStatus);
                logDataProcessor.processLogData(traceStatus.getThreadId(),0,traceStatus.getMethodName(), e.getError().getMessage());
            }
            throw e;
        }catch (Exception e) {
            if (traceStatus != null) {
                logTrace.exception(e, traceStatus);
                logDataProcessor.processLogData(traceStatus.getThreadId(),0, traceStatus.getMethodName(), "internal error");
            }
            throw e;
        }
    }
}
