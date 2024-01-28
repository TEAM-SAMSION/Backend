package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.repository.AssignQueryRepository;
import com.pawith.todoinfrastructure.dao.IncompleteAssignInfoDaoImpl;
import com.pawith.todoinfrastructure.dao.QIncompleteAssignInfoDaoImpl;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AssignRepositoryImpl implements AssignQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Assign> findAllByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate scheduledDate) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = qAssign.todo;
        final QRegister qRegister = qAssign.register;
        return queryFactory.select(qAssign)
            .from(qAssign)
            .join(qTodo).fetchJoin()
            .join(qRegister).fetchJoin()
            .where(qTodo.category.id.eq(categoryId)
                .and(qTodo.scheduledDate.eq(scheduledDate)))
            .orderBy(qTodo.completionStatus.desc())
            .fetch();
    }

    @Override
    public List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = qAssign.todo;
        final QRegister qRegister = qAssign.register;
        final QCategory qCategory = qTodo.category;
        return queryFactory.select(qAssign)
            .from(qAssign)
            .join(qTodo).fetchJoin()
            .join(qCategory).fetchJoin()
            .where(qRegister.userId.eq(userId)
                .and(qRegister.todoTeam.id.eq(todoTeamId))
                .and(qTodo.scheduledDate.eq(scheduledDate))
                .and(qTodo.category.categoryStatus.eq(CategoryStatus.ON)))
            .fetch();
    }

    @Override
    public void deleteByRegisterIdsQuery(final List<Long> registerIds) {
        final QAssign qAssign = QAssign.assign;
        queryFactory.update(qAssign)
            .set(qAssign.isDeleted, true)
            .where(qAssign.register.id.in(registerIds))
            .execute();
    }

    @Override
    public void deleteAllByTodoIdQuery(final Long todoId) {
        final QAssign qAssign = QAssign.assign;
        queryFactory.update(qAssign)
            .set(qAssign.isDeleted, true)
            .where(qAssign.todo.id.eq(todoId))
            .execute();
    }

    @Override
    public Optional<Assign> findByTodoIdAndUserIdQuery(Long todoId, Long userId) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = QTodo.todo;
        final QRegister qRegister = QRegister.register;
        return Optional.ofNullable(queryFactory.select(qAssign)
            .from(qAssign)
            .join(qRegister).on(qRegister.id.eq(qAssign.register.id).and(qRegister.userId.eq(userId)))
            .join(qTodo).on(qTodo.id.eq(qAssign.todo.id).and(qTodo.id.eq(todoId)))
            .fetchOne());
    }

    @Override
    public List<Assign> findAllByTodoIdQuery(Long todoId) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = qAssign.todo;
        return queryFactory.select(qAssign)
            .from(qAssign)
            .join(qTodo).fetchJoin()
            .where(qTodo.id.eq(todoId))
            .fetch();
    }

    @Override
    public List<Assign> findAllByTodoIdWithRegisterFetchQuery(Long todoId) {
        final QAssign qAssign = QAssign.assign;
        final QRegister qRegister = qAssign.register;
        return queryFactory.select(qAssign)
            .from(qAssign)
            .join(qRegister).fetchJoin()
            .where(qAssign.todo.id.eq(todoId))
            .fetch();
    }

    @Override
    @Transactional
    public void deleteAllByCategoryIdQuery(final Long categoryId) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = QTodo.todo;
        queryFactory.update(qAssign)
            .set(qAssign.isDeleted, true)
            .where(qAssign.todo.id.in(JPAExpressions.select(qTodo.id)
                .from(qTodo)
                .where(qTodo.category.id.eq(categoryId))))
            .execute();
    }

    @Override
    public Long countByTodoIdAndCompletedQuery(Long todoId, @Nullable CompletionStatus completionStatus) {
        final QAssign qAssign = QAssign.assign;
        return queryFactory.select(qAssign.count())
            .from(qAssign)
            .where(qAssign.todo.id.eq(todoId)
                , Objects.nonNull(completionStatus) ? qAssign.completionStatus.eq(completionStatus) : null)
            .fetchOne();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IncompleteAssignInfoDaoImpl> findAllAssignInfoByTodoIdAndCompleteStatusQuery(Long todoId, CompletionStatus completionStatus) {
        final QAssign qAssign = QAssign.assign;
        final QRegister qRegister = qAssign.register;
        final QTodo qTodo = qAssign.todo;
        final QTodoTeam qTodoTeam = qRegister.todoTeam;

        return queryFactory.select(new QIncompleteAssignInfoDaoImpl(qRegister.userId, qTodoTeam.teamName, qTodo.description))
            .from(qAssign)
            .join(qRegister)
            .join(qTodo).on(qTodo.id.eq(todoId))
            .join(qTodoTeam)
            .where(qAssign.completionStatus.eq(completionStatus))
            .fetch();
    }
}
