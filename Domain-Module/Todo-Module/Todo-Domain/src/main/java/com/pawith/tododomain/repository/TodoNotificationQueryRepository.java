package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.dao.NotificationDao;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoNotificationQueryRepository {
     <T extends NotificationDao> List<T> findAllWithNotCompletedAssignAndAlarmTimeQuery(Duration criterionTime, LocalDateTime alarmTime, Pageable pageable);

     List<TodoNotification> findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(List<Long> todoId, Long userId);
}
