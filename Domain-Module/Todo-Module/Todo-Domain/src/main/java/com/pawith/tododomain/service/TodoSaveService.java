package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TodoSaveService {

    private final TodoRepository todoRepository;

    public void saveTodoEntity(Todo todo){
        todoRepository.save(todo);
    }
}
