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

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoCreateUseCase {

    private final TodoSaveService todoSaveService;
    private final RegisterQueryService registerQueryService;
    private final CategoryQueryService categoryQueryService;
    private final AssignSaveService assignSaveService;

    public void createTodo(TodoCreateRequest request) {
        final Category category = categoryQueryService.findCategoryById(request.getCategoryId());
        final Todo todo = TodoMapper.mapToTodo(request, category);
        todoSaveService.saveTodoEntity(todo);
        ListIterator<Register> registerListIterator = registerQueryService.findAllRegistersByIds(request.getRegisterIds()).listIterator();
        request.getRegisterIds().forEach(registerId -> {
            assignSaveService.saveAssignEntity(new Assign(todo, registerListIterator.next()));
        });
    }
}
