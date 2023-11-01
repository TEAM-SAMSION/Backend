package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.event.TodoCompletionCheckEvent;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class TodoCompletionCheckOnTodoHandler {
    private final AssignQueryService assignQueryService;
    private final TodoQueryService todoQueryService;

    @EventListener
    public void changeTodoStatus(TodoCompletionCheckEvent todoCompletionCheckEvent){
        final List<Assign> assigns = assignQueryService.findAllAssignByTodoId(todoCompletionCheckEvent.getTodoId());
        final Todo todo = todoQueryService.findTodoByTodoId(todoCompletionCheckEvent.getTodoId());
        CompletionStatus newStatus = assigns.stream()
                .allMatch(assign -> assign.getCompletionStatus() == CompletionStatus.COMPLETE)
                ? CompletionStatus.COMPLETE
                : CompletionStatus.INCOMPLETE;
        todo.updateCompletionStatus(newStatus);
    }
}
