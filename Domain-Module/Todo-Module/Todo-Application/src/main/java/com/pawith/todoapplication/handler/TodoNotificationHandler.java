package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.enums.AlarmCategory;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.commonmodule.schedule.AbstractBatchSchedulingHandler;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import com.pawith.tododomain.repository.dao.NotificationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
public class TodoNotificationHandler extends AbstractBatchSchedulingHandler<NotificationDao> {
    private static final Integer BATCH_SIZE = 100;
    private static final String CRON_EXPRESSION = "0 0 * * * *";
    private static final String FIRST_NOTIFICATION_MESSAGE_FORMAT = "오늘 %s시 %s분, [%s] %s 잊지 않았죠?";
    private static final String SECOND_NOTIFICATION_MESSAGE_FORMAT = "[%s] %s 시작까지 1시간 남았어요!";

    private final TodoNotificationRepository todoNotificationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TodoNotificationHandler(TodoNotificationRepository todoNotificationRepository, ApplicationEventPublisher applicationEventPublisher) {
        super(BATCH_SIZE, CRON_EXPRESSION);
        this.todoNotificationRepository = todoNotificationRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected List<NotificationDao> extractBatchData(Pageable pageable) {
        return todoNotificationRepository.findAllWithNotCompletedAssignAndAlarmTimeQuery(Duration.ofHours(3), getCurrentHourTime(), pageable);
    }

    @Override
    protected void processBatch(List<NotificationDao> executionResult) {
        handleNotification(executionResult, getCurrentHourTime());
    }

    private String buildMessageFromNotification(NotificationDao notification, long diffNotificationTimeWithCurrentTime) {
        if (diffNotificationTimeWithCurrentTime == 3L) {
            return String.format(FIRST_NOTIFICATION_MESSAGE_FORMAT, notification.getNotificationTime().getHour(), notification.getNotificationTime().getMinute(), notification.getCategoryName(), notification.getTodoDescription());
        } else if (diffNotificationTimeWithCurrentTime == 1L) {
            return String.format(SECOND_NOTIFICATION_MESSAGE_FORMAT, notification.getCategoryName(), notification.getTodoDescription());
        }
        return "";
    }

    private void handleNotification(List<NotificationDao> notificationBatch, LocalTime executeTime) {
        notificationBatch.stream().parallel()
            .forEach(notification -> {
                final long diffNotificationTimeWithCurrentTime = Duration.between(executeTime, notification.getNotificationTime()).toHours();
                final String message = buildMessageFromNotification(notification, diffNotificationTimeWithCurrentTime);
                if (StringUtils.hasText(message)) {
                    applicationEventPublisher.publishEvent(new NotificationEvent(notification.getUserId(), AlarmCategory.TODO, message, notification.getTodoTeamId()));
                }
            });
    }

    private LocalTime getCurrentHourTime() {
        return LocalTime.now().withMinute(0).withSecond(0).withNano(0);
    }
}
