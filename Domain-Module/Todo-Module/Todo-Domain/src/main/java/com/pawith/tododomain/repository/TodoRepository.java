package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

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
        "where a.todo.id=t.id and t.scheduledDate = :scheduledDate and t.completionStatus='COMPLETE'"
    )
    Long countCompleteTodoByDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("scheduledDate") LocalDate scheduledDate);

    @Query(value = "select count(t) " +
            "from Todo t " +
            "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
            "join Assign  a on a.register.id=r.id " +
            "where a.todo.id=t.id and t.scheduledDate between :startDate and :endDate"
    )
    Long countTodoByBetweenDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "select count(t) " +
            "from Todo t " +
            "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
            "join Assign  a on a.register.id=r.id " +
            "where a.todo.id=t.id and t.scheduledDate between :startDate and :endDate and t.completionStatus='COMPLETE'"
    )
    Long countCompleteTodoByBetweenDate(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("select t from Todo t " +
            "join Category c on c.id=:categoryId " +
            "where t.category.id = c.id and t.scheduledDate = :moveDate")
    List<Todo> findTodoListByCategoryIdAndscheduledDate(Long categoryId, LocalDate moveDate);

    @Modifying
    @Query("update Todo t set t.isDeleted = true where t.category.id=:categoryId")
    void deleteAllByCategoryId(@Param("categoryId") Long categoryId);

    void deleteById(Long todoId);

    @Query("select t from Todo t " +
            "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
            "join Assign  a on a.register.id=r.id " +
            "where a.todo.id=t.id")
    Slice<Todo> findTodoSliceByUserIdAndTodoTeamId(Long userId, Long todoTeamId, Pageable pageable);

    @Query("select t from Todo t " +
            "join Register r on r.userId=:userId " +
            "join Assign  a on a.register.id=r.id " +
            "where a.todo.id=t.id")
    Slice<Todo> findTodoSliceByUserId(Long userId, Pageable pageable);
}
