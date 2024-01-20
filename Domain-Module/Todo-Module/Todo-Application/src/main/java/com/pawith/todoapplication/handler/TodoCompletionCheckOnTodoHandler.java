package com.pawith.todoapplication.handler;

import com.pawith.todoapplication.handler.event.TodoAssignStatusChangeEvent;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class TodoCompletionCheckOnTodoHandler {
    private final AssignQueryService assignQueryService;
    private final TodoQueryService todoQueryService;

    @EventListener
    public void changeTodoStatus(TodoAssignStatusChangeEvent todoAssignStatusChangeEvent) throws InterruptedException {
        while(true) {
            try {
                final List<Assign> assigns = assignQueryService.findAllAssignByTodoId(todoAssignStatusChangeEvent.todoId());
                final Todo todo = todoQueryService.findTodoByTodoId(todoAssignStatusChangeEvent.todoId());
                final boolean isAllCompleteTodo = assigns.stream().allMatch(Assign::isCompleted);
                todo.updateCompletionStatus(isAllCompleteTodo);
                break;
            } catch (OptimisticLockException e) {
                Thread.sleep(20);
            }
        }
    }
}
