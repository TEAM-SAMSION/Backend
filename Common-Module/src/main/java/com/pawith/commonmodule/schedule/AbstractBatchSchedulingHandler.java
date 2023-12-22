package com.pawith.commonmodule.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

@Slf4j
public abstract class AbstractBatchSchedulingHandler<T> implements BatchSchedulingHandler {
    private final Integer batchSize;

    public AbstractBatchSchedulingHandler(Integer batchSize, String cronExpression) {
        this.batchSize = batchSize;
        final CronTrigger cronTrigger = new CronTrigger(cronExpression);
        final ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
        taskExecutor.initialize();
        taskExecutor.setPoolSize(1);
        taskExecutor.schedule(this::execute, cronTrigger);
    }

    @Override
    public void execute() {
        log.debug("Batch processing is started.");
        final long startTime = System.currentTimeMillis();
        final Pageable pageable = Pageable.ofSize(batchSize);
        List<T> executionResult;
        try {
            do {
                executionResult = extractBatchData(pageable);
                processBatch(executionResult);
                pageable.next();
            } while (!checkingIsFinished(executionResult));
        } catch (Exception e){
            errorHandle(e);
        }
        log.info("Batch processing is finished. Execution time: {}ms", System.currentTimeMillis() - startTime);
    }

    private boolean checkingIsFinished(List<T> executionResult){
        if(executionResult.size() < batchSize){
            log.debug("Batch processing is finished.");
            return true;
        }
        log.debug("Batch processing is not finished. Continue processing...");
        return false;
    }



    protected abstract List<T> extractBatchData(Pageable pageable);
    protected abstract void processBatch(List<T> executionResult);

    protected void errorHandle(Exception e){
        log.error("Batch processing is failed. {}", e.getMessage());
    }
}
