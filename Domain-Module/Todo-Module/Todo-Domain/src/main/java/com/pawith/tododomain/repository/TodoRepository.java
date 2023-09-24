package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "select count(t) " +
        "from Todo t " +
        "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
        "join Assign  a on a.register.id=r.id " +
        "where a.todo.id=t.id and t.scheduledDate = :scheduledDate"
    )
    Long countTodoByDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("scheduledDate") LocalDate scheduledDate);

    @Query(value = "select count(t) " +
        "from Todo t " +
        "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
        "join Assign  a on a.register.id=r.id " +
        "where a.todo.id=t.id and t.scheduledDate = :scheduledDate and t.todoStatus='COMPLETE'"
    )
    Long countCompleteTodoByDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("scheduledDate") LocalDate scheduledDate);


    @Query("select t from Todo t " +
            "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
            "join Assign  a on a.register.id=r.id and t.scheduledDate = :scheduledDate " +
            "where a.todo.id = t.id")
    Slice<Todo> findTodoByDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("scheduledDate") LocalDate scheduledDate, Pageable pageable);
}
