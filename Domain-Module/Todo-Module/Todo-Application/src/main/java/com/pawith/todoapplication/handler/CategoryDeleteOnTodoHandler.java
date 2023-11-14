package com.pawith.todoapplication.handler;

import com.pawith.todoapplication.handler.event.CategoryDeleteEvent;
import com.pawith.tododomain.service.AssignDeleteService;
import com.pawith.tododomain.service.TodoDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class CategoryDeleteOnTodoHandler {
    private final TodoDeleteService todoDeleteService;
    private final AssignDeleteService assignDeleteService;

    @EventListener
    public void deleteAllByCategoryId(CategoryDeleteEvent categoryDeleteEvent) {
        todoDeleteService.deleteTodoByCategoryId(categoryDeleteEvent.getCategoryId());
        assignDeleteService.deleteAssignByCategoryId(categoryDeleteEvent.getCategoryId());
    }
}
