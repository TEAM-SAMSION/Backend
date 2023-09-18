package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.*;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRateGetUseCase {

    private final TodoQueryService todoQueryService;
    private final RegisterQueryService registerQueryService;
    private final AssignQueryService assignQueryService;

    public TodoProgressResponse getTodoProgress(Long todoTeamId) {
        LocalDate serverTime = LocalDate.now();
        User user = UserUtils.getAccessUser();
        Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, user.getId());
        List<Assign> assignList = assignQueryService.findAssignByRegisterIdAndCreatedAtBetween(register.getId(), serverTime.atStartOfDay(), serverTime.atTime(LocalTime.MAX));

        List<Todo> todoList = assignList.stream()
                .map(assign -> todoQueryService.findTodoByTodoId(assign.getTodo().getId()))
                .collect(Collectors.toList());
        Integer totalTodoCount = todoList.size();

        Long doneTodoCount = todoList.stream()
                .filter(todo -> todo.getTodoStatus().equals("COMPLETE"))
                .count();

        int result =  (int)((double)doneTodoCount/totalTodoCount*100);
        return new TodoProgressResponse(result);
    }
}
