package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.dao.NotificationDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public interface TodoNotificationQueryRepository {
     <T extends NotificationDao> Slice<T> findAllWithNotCompletedAssignAndAlarmTimeQuery(Duration criterionTime, LocalTime alarmTime, Pageable pageable);

     List<TodoNotification> findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(List<Long> todoId, Long userId);
}
