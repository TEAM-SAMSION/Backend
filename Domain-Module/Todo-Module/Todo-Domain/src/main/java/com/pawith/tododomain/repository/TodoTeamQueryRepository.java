package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import java.util.List;

public interface TodoTeamQueryRepository {
    List<TodoTeam> findAllByUserIdQuery(Long userId);
    TodoTeam findByTodoId(Long todoId);
}
