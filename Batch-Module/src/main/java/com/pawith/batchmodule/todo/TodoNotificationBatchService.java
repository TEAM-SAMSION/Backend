package com.pawith.batchmodule.todo;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

/**
 * Batch Insert 전 : 1637ms
 * Batch Insert 후 : 1293ms
 * 20 건 기준
 */
@Slf4j
@Component
public class TodoNotificationBatchService extends AbstractBatchSchedulingHandler<NotificationDao> {
    private static final Integer BATCH_SIZE = 100;
    private static final String CRON_EXPRESSION = "0 0 * * * *";

    private final TodoNotificationRepository todoNotificationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TodoNotificationBatchService(TodoNotificationRepository todoNotificationRepository, ApplicationEventPublisher applicationEventPublisher) {
        super(BATCH_SIZE, CRON_EXPRESSION);
        this.todoNotificationRepository = todoNotificationRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected List<NotificationDao> extractBatchData(Pageable pageable) {
        return todoNotificationRepository.findAllWithNotCompletedAssignAndAlarmTimeQuery(Duration.ofHours(3), getCurrentHourDateTime(), pageable);
    }

    @Override
    protected void processBatch(List<NotificationDao> executionResult) {
        handleNotification(executionResult, getCurrentHourDateTime());
    }

    private void handleNotification(List<NotificationDao> notificationBatch, LocalDateTime executeTime) {
        final List<NotificationEvent> notificationEventList = notificationBatch.stream()
            .filter(notification -> NotificationMessage.contains(calculateDiffTimeHour(executeTime, notification)))
            .map(notification -> {
                final long diffNotificationTimeWithCurrentTime = calculateDiffTimeHour(executeTime, notification);
                final String message = NotificationMessage.buildMessage(notification, diffNotificationTimeWithCurrentTime);
                return new NotificationEvent(notification.getUserId(), notification.getTodoTeamName(), message, notification.getTodoTeamId());
            }).toList();
        applicationEventPublisher.publishEvent(new MultiNotificationEvent(notificationEventList));
    }

    private long calculateDiffTimeHour(LocalDateTime executeTime, NotificationDao notification) {
        return Duration.between(executeTime, LocalDateTime.of(notification.getScheduledDate(), notification.getNotificationTime())).toHours();
    }

    private LocalDateTime getCurrentHourDateTime() {
        return LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
    }

    private enum NotificationMessage {
        FIRST_NOTIFICATION_MESSAGE_FORMAT(3L,
            notification -> {
                final String messageFormat = "오늘 %s시 %s분, [%s] %s 잊지 않았죠?";
                return String.format(messageFormat,
                    notification.getNotificationTime().getHour(),
                    notification.getNotificationTime().getMinute(),
                    notification.getCategoryName(),
                    notification.getTodoDescription());
            }),
        SECOND_NOTIFICATION_MESSAGE_FORMAT(1L,
            notification -> {
                final String messageFormat = "[%s] %s 시작까지 1시간 남았어요!";
                return String.format(messageFormat,
                    notification.getCategoryName(),
                    notification.getTodoDescription());
            });

        private static final List<NotificationMessage> values = List.of(values());
        private final Long criticalTime;
        private final Function<NotificationDao, String> buildMessage;

        NotificationMessage(Long criticalTime, Function<NotificationDao, String> buildMessage) {
            this.criticalTime = criticalTime;
            this.buildMessage = buildMessage;
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
            return messageBuilder.buildMessage.apply(notification);
        }
    }
}
