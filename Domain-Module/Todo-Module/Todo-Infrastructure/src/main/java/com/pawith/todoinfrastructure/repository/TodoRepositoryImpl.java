package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.CategoryStatus;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.entity.QAssign;
import com.pawith.tododomain.entity.QCategory;
import com.pawith.tododomain.entity.QRegister;
import com.pawith.tododomain.entity.QTodo;
import com.pawith.tododomain.entity.QTodoTeam;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.repository.TodoQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Todo> findTodoListByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate moveDate) {
        final QTodo todo = QTodo.todo;
        return jpaQueryFactory.selectFrom(todo)
            .where(todo.category.id.eq(categoryId)
                .and(todo.scheduledDate.eq(moveDate)))
            .fetch();
    }

    @Override
    public Long countTodoByDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        final QCategory category = QCategory.category;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id).and(assign.todo.eq(todo)
                        .and(assign.isDeleted.eq(false))))
                .join(category).on(category.eq(todo.category).and(category.categoryStatus.eq(CategoryStatus.ON)))
                .where(todo.scheduledDate.eq(scheduledDate))
                .fetchOne();
    }

    @Override
    public Long countCompleteTodoByDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        final QCategory category = QCategory.category;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id).and(assign.completionStatus.eq(CompletionStatus.COMPLETE))
                        .and(assign.todo.eq(todo).and(assign.isDeleted.eq(false))))
                .join(category).on(category.eq(todo.category).and(category.categoryStatus.eq(CategoryStatus.ON)))
                .where(todo.scheduledDate.eq(scheduledDate))
                .fetchOne();
    }

    @Override
    public Long countTodoByBetweenDateQuery(Long userId, Long todoTeamId, LocalDate startDate, LocalDate endDate) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(todo.scheduledDate.between(startDate, endDate).and(assign.todo.eq(todo)))
                .fetchOne();
    }

    @Override
    public Long countCompleteTodoByBetweenDateQuery(Long userId, Long todoTeamId, LocalDate startDate, LocalDate endDate) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(todo.scheduledDate.between(startDate, endDate).and(assign.todo.eq(todo)).and(todo.completionStatus.eq(CompletionStatus.COMPLETE)))
                .fetchOne();
    }

    @Override
    public void deleteAllByCategoryIdQuery(Long categoryId) {
        final QTodo todo = QTodo.todo;
        jpaQueryFactory
                .update(todo)
                .set(todo.isDeleted, true)
                .where(todo.category.id.eq(categoryId))
                .execute();
    }

    @Override
    public Slice<Todo> findTodoSliceByUserIdAndTodoTeamIdQuery(Long userId, Long todoTeamId, Pageable pageable) {
        final QTodo todo = QTodo.todo;
        final QCategory category = todo.category;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        List<Todo> todos =  jpaQueryFactory.select(todo)
                .from(todo)
                .join(category).fetchJoin()
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(assign.todo.eq(todo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return getSliceImpl(todos, pageable);
    }

    @Override
    public Slice<Todo> findTodoSliceByUserIdQuery(Long userId, Pageable pageable) {
        final QTodo todo = QTodo.todo;
        final QCategory category = todo.category;
        final QTodoTeam todoTeam = category.todoTeam;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        List<Todo> todos = jpaQueryFactory.select(todo)
                .from(todo)
                .join(category).fetchJoin()
                .join(todoTeam).fetchJoin()
                .join(register).on(register.userId.eq(userId))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(assign.todo.eq(todo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return getSliceImpl(todos, pageable);
    }

    @Override
    public Integer countTodoByUserIdAndTodoTeamIdQuery(Long userId, Long todoTeamId) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId).and(register.todoTeam.id.eq(todoTeamId)))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(assign.todo.eq(todo))
                .fetchOne().intValue();
    }

    @Override
    public Integer countTodoByUserIdQuery(Long userId) {
        final QTodo todo = QTodo.todo;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        return jpaQueryFactory.select(todo.count())
                .from(todo)
                .join(register).on(register.userId.eq(userId))
                .join(assign).on(assign.register.id.eq(register.id))
                .where(assign.todo.eq(todo))
                .fetchOne().intValue();
    }

    private <T> Slice<T> getSliceImpl(List<T> list, Pageable pageable) {
        boolean hasNext = false;
        if (list.size() > pageable.getPageSize()) {
            hasNext = true;
            list.remove(list.size() - 1);
        }

        return new SliceImpl<>(list, pageable, hasNext);
    }

}
