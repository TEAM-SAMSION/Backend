package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Long countByCategoryIdAndCreatedAt(Long categoryId, LocalDateTime createdAt);

    Long countByCategoryIdAndCreatedAtAndTodoStatus(Long categoryId, LocalDateTime createdAt, String status);

    @Query(value = "select count(t) " +
        "from Todo t " +
        "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
        "join Assign  a on a.register.id=r.id " +
        "where a.todo.id=t.id and date(a.createdAt) = date(now())"
    )
    Long countTodayTodo(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId);

    @Query(value = "select count(t) " +
        "from Todo t " +
        "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
        "join Assign  a on a.register.id=r.id " +
        "where a.todo.id=t.id and date(a.createdAt) = date(now()) and t.todoStatus='COMPLETE'"
    )
    Long countTodayCompleteTodo(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId);

}
