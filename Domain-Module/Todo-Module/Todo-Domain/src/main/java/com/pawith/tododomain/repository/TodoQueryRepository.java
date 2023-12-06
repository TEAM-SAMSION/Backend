package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TodoQueryRepository {
    List<Todo> findTodoListByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate moveDate);
    Long countTodoByDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate);
    Long countCompleteTodoByDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate);
    Long countTodoByBetweenDateQuery(Long userId, Long todoTeamId, LocalDate startDate, LocalDate endDate);
    Long countCompleteTodoByBetweenDateQuery(Long userId, Long todoTeamId, LocalDate startDate, LocalDate endDate);
    void deleteAllByCategoryIdQuery(Long categoryId);
    Slice<Todo> findTodoSliceByUserIdAndTodoTeamIdQuery(Long userId, Long todoTeamId, Pageable pageable);
    Slice<Todo> findTodoSliceByUserIdQuery(Long userId, Pageable pageable);
    Integer countTodoByUserIdAndTodoTeamIdQuery(Long userId, Long todoTeamId);
    Integer countTodoByUserIdQuery(Long userId);
}
