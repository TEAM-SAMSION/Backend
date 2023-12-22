package com.pawith.event.christmas;

import com.pawith.commonmodule.schedule.AbstractBatchSchedulingHandler;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class ChristmasEventPublishHandler extends AbstractBatchSchedulingHandler<TodoTeam> {
    private static final Integer BATCH_SIZE = 100;
    //    private static final String CRON_EXPRESSION = "0 0 0 23 12 *";
    private static final String CRON_EXPRESSION = "0 0 19 22 12 *";
    private final TodoTeamRepository todoTeamRepository;
    private final ChristmasEventService christmasEventService;
    public ChristmasEventPublishHandler(TodoTeamRepository todoTeamRepository, ChristmasEventService christmasEventService) {
        super(BATCH_SIZE, CRON_EXPRESSION);
        this.todoTeamRepository = todoTeamRepository;
        this.christmasEventService = christmasEventService;
    }

    @Override
    protected List<TodoTeam> extractBatchData(Pageable pageable) {
        return todoTeamRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    protected void processBatch(List<TodoTeam> executionResult) {
        executionResult.forEach(christmasEventService::publishEvent);
    }

}
