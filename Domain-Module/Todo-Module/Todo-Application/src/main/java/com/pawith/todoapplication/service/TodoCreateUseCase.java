package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.mapper.TodoMapper;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignSaveService;
import com.pawith.tododomain.service.CategoryQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoCreateUseCase {

    private final TodoSaveService todoSaveService;
    private final RegisterQueryService registerQueryService;
    private final CategoryQueryService categoryQueryService;
    private final AssignSaveService assignSaveService;

    public void createTodo(TodoCreateRequest request) {
        final ListIterator<CompletableFuture<Register>> registers = request.getRegisterIds().stream()
            .map(registerQueryService::findRegisterByIdAsync)
            .collect(Collectors.toList())
            .listIterator();
        final Category category = categoryQueryService.findCategoryById(request.getCategoryId());
        Todo todo = TodoMapper.mapToTodo(request, category);
        todoSaveService.saveTodoEntity(todo);
        request.getRegisterIds().forEach(registerId -> {
            assignSaveService.saveAssignEntity(new Assign(todo, registers.next().join()));
        });
    }
}
