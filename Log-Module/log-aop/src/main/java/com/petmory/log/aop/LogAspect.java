package com.petmory.log.aop;

import com.petmory.log.webclient.LogDataProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final LogTrace logTrace;
    private final LogDataProcessor logDataProcessor;


    public LogAspect(LogTrace logTrace, LogDataProcessor logDataProcessor) {
        this.logTrace = logTrace;
        this.logDataProcessor = logDataProcessor;
    }

    @Around("com.petmory.log.aop.Pointcuts.allService()")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    @Around("com.petmory.log.aop.Pointcuts.allController()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    private Object getObject(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.start(joinPoint.getSignature().getName());
            Object result = joinPoint.proceed();
            Integer executionTime = logTrace.end(traceStatus);
            logDataProcessor.processLogData(traceStatus.getThreadId(), executionTime, traceStatus.getMethodName(), null,true);
            return result;
        } catch (BusinessException e) {
            if (traceStatus != null) {
                logTrace.apiException(e, traceStatus);
                logDataProcessor.processLogData(traceStatus.getThreadId(),0,traceStatus.getMethodName(), e.getErrorCode().getMessage(),false);
            }
            throw e;
        }catch (Exception e) {
            if (traceStatus != null) {
                logTrace.exception(e, traceStatus);
                logDataProcessor.processLogData(traceStatus.getThreadId(),0, traceStatus.getMethodName(), "internal error",false);
            }
            throw e;
        }
    }
}
