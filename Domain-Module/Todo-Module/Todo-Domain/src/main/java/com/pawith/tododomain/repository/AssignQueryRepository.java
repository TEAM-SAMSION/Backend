package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignQueryRepository {
    List<Assign> findAllByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate scheduledDate);
    List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDateQuery(Long userId, Long todoTeamId, LocalDate scheduledDate);
    void deleteByRegisterIdsQuery(final List<Long> registerIds);
    void deleteAllByTodoIdQuery(final Long todoId);
    Optional<Assign> findByTodoIdAndUserIdQuery(Long todoId, Long userId);
    List<Assign> findAllByTodoIdQuery(Long todoId);
    void deleteAllByCategoryIdQuery(final Long categoryId);
}
