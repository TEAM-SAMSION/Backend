package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoValidateResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.tododomain.service.TodoValidateService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoValidationUseCase {
    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final RegisterQueryService registerQueryService;
    private final TodoValidateService todoValidateService;

    public TodoValidateResponse validateDeleteAndUpdateTodoByTodoId(Long todoTeamId, Long todoId) {
        final User accessUser = userUtils.getAccessUser();
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, accessUser.getId());
        boolean isNotValidate = todoValidateService.validateDeleteAndUpdate(todo, register);
        return new TodoValidateResponse(isNotValidate);
    }
}
