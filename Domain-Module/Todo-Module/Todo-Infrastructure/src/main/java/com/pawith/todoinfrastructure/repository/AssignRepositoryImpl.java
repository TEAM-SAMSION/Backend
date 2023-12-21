package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.CategoryStatus;
import com.pawith.tododomain.entity.QAssign;
import com.pawith.tododomain.entity.QCategory;
import com.pawith.tododomain.entity.QRegister;
import com.pawith.tododomain.entity.QTodo;
import com.pawith.tododomain.repository.AssignQueryRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
}
