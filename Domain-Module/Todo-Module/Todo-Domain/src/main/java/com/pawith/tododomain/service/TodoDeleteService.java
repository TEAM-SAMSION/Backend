package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoDeleteService {

    private final TodoRepository todoRepository;

    public void deleteTodoByTodoId(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public void deleteTodoByCategoryId(Long categoryId) {
        todoRepository.deleteAllByCategoryId(categoryId);
    }
}
