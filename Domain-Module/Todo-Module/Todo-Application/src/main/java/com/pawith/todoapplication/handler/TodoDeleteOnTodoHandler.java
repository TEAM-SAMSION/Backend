package com.pawith.todoapplication.handler;

import com.pawith.todoapplication.handler.event.TodoDeleteEvent;
import com.pawith.tododomain.service.AssignDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class TodoDeleteOnTodoHandler {
    private final AssignDeleteService assignDeleteService;

    @EventListener
    public void deleteAllByTodoId(TodoDeleteEvent todoDeleteEvent) {
        assignDeleteService.deleteAllByTodoId(todoDeleteEvent.getTodoId());
    }
}
