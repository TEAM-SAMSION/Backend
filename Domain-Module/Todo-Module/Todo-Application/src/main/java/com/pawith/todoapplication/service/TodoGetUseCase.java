package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.AssignUserInfoResponse;
import com.pawith.todoapplication.dto.response.TodoCompletionResponse;
import com.pawith.todoapplication.dto.response.TodoInfoResponse;
import com.pawith.todoapplication.dto.response.CategorySubTodoResponse;
import com.pawith.todoapplication.dto.response.CategorySubTodoListResponse;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final UserQueryService userQueryService;
    private final RegisterQueryService registerQueryService;
    private final AssignQueryService assignQueryService;

    public SliceResponse<TodoInfoResponse> getTodoListByTodoTeamId(final Long todoTeamId, final Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice<Todo> todoList = todoQueryService.findTodayTodoSlice(user.getId(), todoTeamId, pageable);
        Slice<TodoInfoResponse> todoHomeResponseSlice = todoList.map(todo -> new TodoInfoResponse(todo.getId(), todo.getDescription(), todo.getCompletionStatus().name()));
        return SliceResponse.from(todoHomeResponseSlice);
    }


    public CategorySubTodoListResponse getTodoListByCategoryId(Long categoryId, LocalDate moveDate) {
        final List<Long> userIds = registerQueryService.findUserIdsByCategoryId(categoryId);
        final Map<Long, User> userMap = userQueryService.findUserMapByIds(userIds);
        final Map<Todo, List<Register>> groupByTodo = getTodoMap(categoryId, moveDate);
        final ArrayList<CategorySubTodoResponse> todoMainResponses = new ArrayList<>();
        for (Todo todo : groupByTodo.keySet()) {
            final List<Register> registers = groupByTodo.get(todo);
            ArrayList<AssignUserInfoResponse> assignUserInfoResponses = new ArrayList<>();
            for (Register register : registers) {
                final User findUser = userMap.get(register.getUserId());
                assignUserInfoResponses.add(new AssignUserInfoResponse(findUser.getId(), findUser.getNickname()));
            }
            todoMainResponses.add(new CategorySubTodoResponse(todo.getId(), todo.getDescription(), todo.getCompletionStatus().name(), assignUserInfoResponses));
        }
        return new CategorySubTodoListResponse(todoMainResponses);
    }

    public TodoCompletionResponse getTodoCompletion(Long todoId) {
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        return new TodoCompletionResponse(todo.getCompletionStatus().name());
    }

    private Map<Todo, List<Register>> getTodoMap(Long categoryId, LocalDate moveDate) {
        return assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, moveDate)
            .stream()
            .collect(Collectors.groupingBy(Assign::getTodo, LinkedHashMap::new,Collectors.mapping(Assign::getRegister, Collectors.toList())));
    }
}
