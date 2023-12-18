package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.repository.TodoNotificationQueryRepository;
import com.pawith.todoinfrastructure.dao.NotificationDaoImpl;
import com.pawith.todoinfrastructure.dao.QNotificationDaoImpl;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TodoNotificationRepositoryImpl implements TodoNotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<NotificationDaoImpl> findAllWithNotCompletedAssignAndAlarmTimeQuery(final Duration criterionTime, final LocalDateTime alarmTime, final Pageable pageable) {
        final QTodoNotification todoNotification = QTodoNotification.todoNotification;
        final QAssign assign = QAssign.assign;
        final QTodo todo = assign.todo;
        final QRegister register = assign.register;
        final QCategory category = todo.category;
        final DateTimeTemplate<LocalDateTime> localDateTimeTemplate =
            Expressions.dateTimeTemplate(LocalDateTime.class, "timestamp({0},{1})", todo.scheduledDate,todoNotification.notificationTime);
        return jpaQueryFactory.select(new QNotificationDaoImpl(register.todoTeam.id, register.userId, category.name, todo.description, todoNotification.notificationTime, todo.scheduledDate))
            .from(todoNotification)
            .join(todoNotification.assign, assign)
            .join(todo)
            .join(category)
            .join(register)
            .where(localDateTimeTemplate.between(alarmTime, alarmTime.plus(criterionTime))
                .and(assign.completionStatus.eq(CompletionStatus.INCOMPLETE))
                .and(register.isRegistered.eq(true)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public List<TodoNotification> findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(List<Long> todoId, Long userId) {
        final QTodoNotification todoNotification = QTodoNotification.todoNotification;
        final QAssign assign = todoNotification.assign;
        final QRegister register = assign.register;
        return jpaQueryFactory.selectFrom(todoNotification)
            .join(assign)
            .join(register).on(register.userId.eq(userId))
            .where(assign.completionStatus.eq(CompletionStatus.INCOMPLETE)
                .and(assign.todo.id.in(todoId))
                .and(register.isRegistered.eq(true)))
            .fetch();
    }
}
