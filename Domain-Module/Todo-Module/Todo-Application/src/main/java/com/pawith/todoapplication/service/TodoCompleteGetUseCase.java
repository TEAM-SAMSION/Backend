package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoCompletionResponse;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.TodoQueryService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class TodoCompleteGetUseCase {
    private final TodoQueryService todoQueryService;

    public TodoCompletionResponse getTodoCompletion(Long todoId) {
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        return new TodoCompletionResponse(todo.getCompletionStatus());
    }
}
