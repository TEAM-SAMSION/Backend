package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import java.util.List;
import java.util.Optional;

public interface TodoTeamQueryRepository {
    List<TodoTeam> findAllByUserIdQuery(Long userId);
    Optional<TodoTeam> findByTodoId(Long todoId);
}
