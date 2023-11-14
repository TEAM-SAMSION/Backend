package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.ScheduledDateChangeRequest;
import com.pawith.todoapplication.dto.request.TodoDescriptionChangeRequest;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.WrongScheduledDateException;
import com.pawith.tododomain.service.TodoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoChangeUseCase {

    private final TodoQueryService todoQueryService;

    public void changeScheduledDate(Long todoId, ScheduledDateChangeRequest request) {
        LocalDate now = LocalDate.now();
        if(request.getScheduledDate().isBefore(now)) throw new WrongScheduledDateException(TodoError.WRONG_SCHEDULED_DATE);
        Todo todo = todoQueryService.findTodoByTodoId(todoId);
        todo.updateScheduledDate(request.getScheduledDate());
    }

    public void changeTodoName(Long todoId, TodoDescriptionChangeRequest request) {
        Todo todo = todoQueryService.findTodoByTodoId(todoId);
        todo.updateDescription(request.getDescription());
    }

}
