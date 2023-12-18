package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.enums.AlarmCategory;
import com.pawith.commonmodule.event.MultiNotificationEvent;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.commonmodule.schedule.AbstractBatchSchedulingHandler;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import com.pawith.tododomain.repository.dao.NotificationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

/**
 * Batch Insert 전 : 1637ms
 * Batch Insert 후 : 1293ms
 * 20 건 기준
 */
@Slf4j
@Component
public class TodoNotificationHandler extends AbstractBatchSchedulingHandler<NotificationDao> {
    private static final Integer BATCH_SIZE = 100;
    private static final String CRON_EXPRESSION = "0 0 * * * *";

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

    private void handleNotification(List<NotificationDao> notificationBatch, LocalTime executeTime) {
        final List<NotificationEvent> notificationEventList = notificationBatch.stream()
            .filter(notification -> NotificationMessage.contains(Duration.between(executeTime, notification.getNotificationTime()).toHours()))
            .map(notification -> {
                final long diffNotificationTimeWithCurrentTime = Duration.between(executeTime, notification.getNotificationTime()).toHours();
                final String message = NotificationMessage.buildMessage(notification, diffNotificationTimeWithCurrentTime);
                return new NotificationEvent(notification.getUserId(), AlarmCategory.TODO, message, notification.getTodoTeamId());
            }).toList();
        applicationEventPublisher.publishEvent(new MultiNotificationEvent(notificationEventList));
    }

    private LocalTime getCurrentHourTime() {
        return LocalTime.now().withMinute(0).withSecond(0).withNano(0);
    }

    private enum NotificationMessage {
        FIRST_NOTIFICATION_MESSAGE_FORMAT(1L, "오늘 %s시 %s분, [%s] %s 잊지 않았죠?"),
        SECOND_NOTIFICATION_MESSAGE_FORMAT(3L, "[%s] %s 시작까지 1시간 남았어요!");

        private static final List<NotificationMessage> values = List.of(values());
        private final Long criticalTime;
        private final String messageFormat;

        NotificationMessage(Long criticalTime, String messageFormat) {
            this.criticalTime = criticalTime;
            this.messageFormat = messageFormat;
        }

        public static Boolean contains(Long diffTime) {
            return values.stream()
                .anyMatch(notificationMessage -> notificationMessage.criticalTime.equals(diffTime));
        }

        public static String buildMessage(NotificationDao notification, long diffTime) {
            NotificationMessage messageBuilder = values.stream()
                .filter(notificationMessage -> notificationMessage.criticalTime.equals(diffTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 시간에 맞는 메시지가 없습니다."));
            return String.format(messageBuilder.messageFormat,
                notification.getNotificationTime().getHour(),
                notification.getNotificationTime().getMinute(),
                notification.getCategoryName(),
                notification.getTodoDescription());
        }
    }
}
