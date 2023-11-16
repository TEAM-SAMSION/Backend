package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.handler.event.TodoCompletionCheckEvent;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class AssignChangeUseCase {

    private final AssignQueryService assignQueryService;
    private final TodoQueryService todoQueryService;
    private final UserUtils userUtils;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void changeAssignStatus(Long todoId){
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        final User user = userUtils.getAccessUser();
        final Assign assign = assignQueryService.findAssignByTodoIdAndUserId(todo.getId(), user.getId());
        assign.updateCompletionStatus();
        applicationEventPublisher.publishEvent(new TodoCompletionCheckEvent(todo.getId()));
    }
}
