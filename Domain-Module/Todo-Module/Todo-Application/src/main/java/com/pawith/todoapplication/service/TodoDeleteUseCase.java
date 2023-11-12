package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.service.TodoDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoDeleteUseCase {

    private final TodoDeleteService todoDeleteService;

    public void deleteTodoByTodoId(Long todoId) {
        todoDeleteService.deleteTodoByTodoId(todoId);
    }


}
