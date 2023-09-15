package com.pawith.log.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawith.commonmodule.exception.BusinessException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LogTrace {

    private ThreadLocal<String> threadId = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(LogTrace.class);

    private final ObjectMapper objectMapper;


    public TraceStatus start(String fullClassName, String method) {
        syncTrace();
        String id = threadId.get();
        long startTime = System.currentTimeMillis();
        int lastDotIndex = fullClassName.lastIndexOf(".");
        String className = fullClassName.substring(lastDotIndex + 1);
        return new TraceStatus(id, startTime, className, method);
    }

    @SneakyThrows
    public void end(TraceStatus traceStatus) {
        final LogFormat logFormat = LogFormat.createLogFormat(traceStatus);
        final String log = objectMapper.writeValueAsString(logFormat);
        if (logFormat.getExecuteTime() > 1000) {
            logger.warn(log);
        } else {
            logger.info(log);
        }
    }

    @SneakyThrows
    public void exception(Exception exception, TraceStatus traceStatus){
        final LogFormat errorLogFormat = LogFormat.createErrorLogFormat(traceStatus, exception);
        final String errorLog = objectMapper.writeValueAsString(errorLogFormat);
        logger.error(errorLog);
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

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class LogFormat{
        private final String threadId;
        private final String className;
        private final String methodName;
        private final Long executeTime;
        private final Integer errorCode;
        private final String errorMessage;
        private final Class<? extends Exception> errorClass;
        private final StackTraceElement[] errorStackTrace;

        public static LogFormat createLogFormat(TraceStatus traceStatus){
            return LogFormat.builder()
                .threadId(traceStatus.getThreadId())
                .className(traceStatus.getClassName())
                .methodName(traceStatus.getMethodName())
                .executeTime(System.currentTimeMillis()-traceStatus.getStartTime())
                .build();
        }

        public static LogFormat createErrorLogFormat(TraceStatus traceStatus, Exception exception){
            LogFormatBuilder logFormatBuilder = LogFormat.builder()
                .threadId(traceStatus.getThreadId())
                .className(traceStatus.getClassName())
                .methodName(traceStatus.getMethodName());
            if(exception instanceof BusinessException){
                return logFormatBuilder
                    .errorCode(((BusinessException) exception).getErrorCode())
                    .errorMessage(((BusinessException) exception).getError().getMessage())
                    .build();
            }else{
                return logFormatBuilder
                    .errorClass(exception.getClass())
                    .errorMessage(exception.getMessage())
                    .errorStackTrace(exception.getStackTrace())
                    .build();
            }
        }
    }
}