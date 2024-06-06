package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.AssignChangeRequest;
import com.pawith.todoapplication.handler.event.TodoAssignStatusChangeEvent;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.*;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class AssignChangeUseCase {

    private final AssignQueryService assignQueryService;
    private final TodoQueryService todoQueryService;
    private final AssignDeleteService assignDeleteService;
    private final AssignSaveService assignSaveService;
    private final RegisterQueryService registerQueryService;
    private final UserUtils userUtils;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void changeAssignStatus(Long todoId){
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        final User user = userUtils.getAccessUser();
        final Assign assign = assignQueryService.findAssignByTodoIdAndUserId(todo.getId(), user.getId());
        assign.updateCompletionStatus();
        applicationEventPublisher.publishEvent(new TodoAssignStatusChangeEvent(todo.getId()));
    }

    public void changeAssign(Long todoId, AssignChangeRequest request) {
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        final List<Assign> assignList = assignQueryService.findAllAssignWithRegisterByTodoId(todoId);

        List<Long> existingAssigneeIds = assignList.stream()
                .map(assign -> assign.getRegister().getId())
                .collect(toList());

        List<Long> addedAssigneeIds = findAddedAssignees(request.getRegisterIds(), existingAssigneeIds);
        List<Long> removedAssigneeIds = findRemovedAssignees(request.getRegisterIds(), existingAssigneeIds);

        saveNewAssignees(todo, addedAssigneeIds);
        deleteRemovedAssignees(removedAssigneeIds);
    }

    private List<Long> findAddedAssignees(List<Long> requestAssigneeIds, List<Long> existingAssigneeIds) {
        return requestAssigneeIds.stream()
                .filter(assigneeId -> !existingAssigneeIds.contains(assigneeId))
                .collect(toList());
    }

    private List<Long> findRemovedAssignees(List<Long> requestAssigneeIds, List<Long> existingAssigneeIds) {
        return existingAssigneeIds.stream()
                .filter(assigneeId -> !requestAssigneeIds.contains(assigneeId))
                .collect(toList());
    }

    private void saveNewAssignees(Todo todo, List<Long> addedAssigneeIds) {
        List<Register> addRegisterList = registerQueryService.findAllRegistersByIds(addedAssigneeIds);
        addRegisterList.forEach(register ->
                assignSaveService.saveAssignEntity(new Assign(todo, register))
        );
    }

    private void deleteRemovedAssignees(List<Long> removedAssigneeIds) {
        assignDeleteService.deleteAssignByRegisterId(removedAssigneeIds);
    }
}
