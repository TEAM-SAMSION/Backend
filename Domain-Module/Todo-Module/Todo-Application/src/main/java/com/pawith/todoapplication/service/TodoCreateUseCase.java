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
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoCreateUseCase {

    private final TodoSaveService todoSaveService;
    private final RegisterQueryService registerQueryService;
    private final CategoryQueryService categoryQueryService;
    private final AssignSaveService assignSaveService;
    private final UserUtils userUtils;

    public void createTodo(TodoCreateRequest request) {
        final User accessUser = userUtils.getAccessUser();
        final Category category = categoryQueryService.findCategoryByCategoryId(request.getCategoryId());
        final List<Register> registers = registerQueryService.findAllRegistersByIds(request.getRegisterIds());
        final Register todoCreateRegister = getTodoCreateRegister(request, registers, accessUser);
        final Todo todo = TodoMapper.mapToTodo(request, category, todoCreateRegister.getId());
        final List<Assign> assignList = registers.stream()
            .map(register -> new Assign(todo, register))
            .toList();
        todoSaveService.saveTodoEntity(todo);
        assignSaveService.saveAllAssignEntity(assignList);
    }

    private Register getTodoCreateRegister(TodoCreateRequest request, List<Register> registers, User accessUser) {
        return registers.stream()
            .filter(register -> register.matchUserId(accessUser.getId()))
            .findFirst()
            .orElseGet(() -> registerQueryService.findRegisterByNullableTodoTeamIdAndUserIdAndCategoryId(request.getTodoTeamId(), accessUser.getId(), request.getCategoryId()));
    }
}
