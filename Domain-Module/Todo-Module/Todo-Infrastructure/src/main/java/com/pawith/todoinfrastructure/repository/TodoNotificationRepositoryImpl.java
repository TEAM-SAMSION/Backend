package com.pawith.todoinfrastructure.repository;

import com.pawith.commonmodule.util.SliceUtils;
import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.repository.TodoNotificationQueryRepository;
import com.pawith.todoinfrastructure.dao.NotificationDaoImpl;
import com.pawith.todoinfrastructure.dao.QNotificationDaoImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TodoNotificationRepositoryImpl implements TodoNotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Slice<NotificationDaoImpl> findAllWithNotCompletedAssignAndAlarmTimeQuery(final Duration criterionTime, final LocalTime alarmTime, final Pageable pageable) {
        final QTodoNotification todoNotification = QTodoNotification.todoNotification;
        final QAssign assign = QAssign.assign;
        final QTodo todo = assign.todo;
        final QRegister register = assign.register;
        final QCategory category = todo.category;
        final List<NotificationDaoImpl> notificationDaoList =
            jpaQueryFactory.select(new QNotificationDaoImpl(register.todoTeam.id, register.userId, category.name, todo.description, todoNotification.notificationTime))
                .from(todoNotification)
                .innerJoin(todoNotification.assign, assign)
                .innerJoin(todo)
                .innerJoin(category)
                .innerJoin(register)
                .where(todoNotification.notificationTime.between(alarmTime, alarmTime.plus(criterionTime))
                    .and(todoNotification.assign.eq(assign))
                    .and(todo.scheduledDate.eq(LocalDate.now()))
                    .and(assign.completionStatus.eq(CompletionStatus.INCOMPLETE))
                    .and(register.isRegistered.eq(true)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return SliceUtils.getSliceImpl(notificationDaoList, pageable);
    }

    @Override
    public List<TodoNotification> findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(List<Long> todoId, Long userId) {
        final QTodoNotification todoNotification = QTodoNotification.todoNotification;
        final QAssign assign = todoNotification.assign;
        final QRegister register = assign.register;
        return jpaQueryFactory.selectFrom(todoNotification)
            .innerJoin(assign)
            .innerJoin(register).on(register.userId.eq(userId))
            .where(assign.completionStatus.eq(CompletionStatus.INCOMPLETE)
                .and(assign.todo.id.in(todoId))
                .and(register.isRegistered.eq(true)))
            .fetch();
    }
}
