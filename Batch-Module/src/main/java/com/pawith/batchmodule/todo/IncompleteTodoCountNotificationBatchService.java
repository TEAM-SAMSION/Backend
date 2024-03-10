package com.pawith.batchmodule.todo;

import com.pawith.commonmodule.cache.operators.ValueOperator;
import com.pawith.commonmodule.event.MultiNotificationEvent;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.commonmodule.schedule.AbstractBatchSchedulingHandler;
import com.pawith.tododomain.repository.RegisterRepository;
import com.pawith.tododomain.repository.dao.IncompleteTodoCountInfoDao;
import com.pawith.userdomain.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class IncompleteTodoCountNotificationBatchService extends AbstractBatchSchedulingHandler<IncompleteTodoCountInfoDao> {
    private static final Integer BATCH_SIZE = 100;
    private static final String CRON_EXPRESSION = "0 0 20 * * *"; // 매일 20시에 실행
    private static final String NOTIFICATION_MESSAGE = "[%s] 오늘이 지나기 전, %s님에게 남은 %d개의 todo를 완료해주세요!";

    private final RegisterRepository registerRepository;
    private final UserQueryService userQueryService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ValueOperator<Long, String> valueOperator;

    public IncompleteTodoCountNotificationBatchService(RegisterRepository registerRepository, UserQueryService userQueryService, ApplicationEventPublisher applicationEventPublisher, ValueOperator<Long, String> valueOperator) {
        super(BATCH_SIZE, CRON_EXPRESSION);
        this.registerRepository = registerRepository;
        this.userQueryService = userQueryService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.valueOperator = valueOperator;
    }

    @Override
    protected List<IncompleteTodoCountInfoDao> extractBatchData(Pageable pageable) {
        return registerRepository.findAllIncompleteTodoCountInfoQuery(pageable);
    }

    @Override
    protected void processBatch(List<IncompleteTodoCountInfoDao> executionResult) {
        cachingUserInfo(executionResult);
        final List<NotificationEvent> notificationEventList = executionResult.stream()
            .filter(incompleteTodoCountInfoDao -> incompleteTodoCountInfoDao.getIncompleteTodoCount() > 0)
            .map(incompleteTodoCountInfoDao -> {
                final String userNickname = valueOperator.get(incompleteTodoCountInfoDao.getUserId());
                final String message = String.format(NOTIFICATION_MESSAGE, incompleteTodoCountInfoDao.getTodoTeamName(), userNickname, incompleteTodoCountInfoDao.getIncompleteTodoCount());
                return new NotificationEvent(incompleteTodoCountInfoDao.getUserId(), incompleteTodoCountInfoDao.getTodoTeamName(), message, incompleteTodoCountInfoDao.getTodoTeamId());
            }).toList();
        applicationEventPublisher.publishEvent(new MultiNotificationEvent(notificationEventList));
    }

    private void cachingUserInfo(List<IncompleteTodoCountInfoDao> executionResult) {
        final List<Long> userIds = executionResult.stream()
            .map(IncompleteTodoCountInfoDao::getUserId)
            .filter(userId -> !valueOperator.contains(userId))
            .toList();
        userQueryService.findMapWithUserIdKeyByIds(userIds)
            .forEach((userId, user) -> valueOperator.setWithExpire(userId, user.getNickname(), 1, TimeUnit.MINUTES));
    }
}
