package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.QTodo;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.repository.TodoQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Todo> findTodoListByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate moveDate) {
        QTodo todo = QTodo.todo;
        return jpaQueryFactory.selectFrom(todo)
            .where(todo.category.id.eq(categoryId)
                .and(todo.scheduledDate.eq(moveDate)))
            .fetch();
    }
}
