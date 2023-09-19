package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoNotFoundException;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public Todo findTodoByTodoId(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(Error.TODO_NOT_FOUND));
    }
}
