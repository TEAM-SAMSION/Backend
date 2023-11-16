package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.enums.AlarmCategory;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import com.pawith.tododomain.repository.dao.NotificationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalTime;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class TodoNotificationHandler {
    private static final Integer BATCH_SIZE = 100;
    private static final String FIRST_NOTIFICATION_MESSAGE_FORMAT = "오늘 %s시 %s분,[%s] %s 잊지 않았죠?";
    private static final String SECOND_NOTIFICATION_MESSAGE_FORMAT = "[%s] %s 시작까지 1시간 남았어요!";

    private final TodoNotificationRepository todoNotificationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static String buildMessageFromNotification(NotificationDao notification, long diffNotificationTimeWithCurrentTime) {
        if (diffNotificationTimeWithCurrentTime == 3L) {
            return String.format(FIRST_NOTIFICATION_MESSAGE_FORMAT, notification.getNotificationTime().getHour(), notification.getNotificationTime().getMinute(), notification.getCategoryName(), notification.getTodoDescription());
        } else if (diffNotificationTimeWithCurrentTime == 1L) {
            return String.format(SECOND_NOTIFICATION_MESSAGE_FORMAT, notification.getCategoryName(), notification.getTodoDescription());
        }
        return "";
    }

    @Scheduled(cron = "0 0 * * * *")
    public void sendTodoNotification() {
        int page = 0;
        boolean hasNext = true;
        do {
            final PageRequest pageRequest = PageRequest.of(page, BATCH_SIZE);
            final Slice<NotificationDao> notificationBatch = todoNotificationRepository.findAllWithNotCompletedAssignAndTodayScheduledTodo(Duration.ofHours(3), pageRequest);
            handleNotification(notificationBatch);
            hasNext = notificationBatch.hasNext();
            page++;
        } while (hasNext);
    }

    private void handleNotification(Slice<NotificationDao> notificationBatch) {
        notificationBatch.stream().parallel()
            .forEach(notification -> {
                final long diffNotificationTimeWithCurrentTime = diffNotificationTimeWithCurrentTime(notification.getNotificationTime());
                String message = buildMessageFromNotification(notification, diffNotificationTimeWithCurrentTime);
                if (StringUtils.hasText(message)) {
                    applicationEventPublisher.publishEvent(
                        new NotificationEvent(notification.getUserId(),
                            AlarmCategory.TODO,
                            message,
                            notification.getTodoTeamId()));
                }
            });
    }

    private Long diffNotificationTimeWithCurrentTime(LocalTime notificationTime) {
        return Duration.between(LocalTime.now(), notificationTime).toHours();
    }
}
