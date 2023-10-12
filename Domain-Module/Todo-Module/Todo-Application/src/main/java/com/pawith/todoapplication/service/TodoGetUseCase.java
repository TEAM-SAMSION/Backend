package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.AssignUserInfoResponse;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.CategorySubTodoResponse;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final UserQueryService userQueryService;
    private final RegisterQueryService registerQueryService;
    private final AssignQueryService assignQueryService;

    public SliceResponse<TodoHomeResponse> getTodoListByTodoTeamId(final Long todoTeamId, final Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice<Todo> todoList = todoQueryService.findTodayTodoSlice(user.getId(), todoTeamId, pageable);
        Slice<TodoHomeResponse> todoHomeResponseSlice = todoList.map(todo -> new TodoHomeResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name()));
        return SliceResponse.from(todoHomeResponseSlice);
    }


    public List<CategorySubTodoResponse> getTodoListByCategoryId(Long categoryId, LocalDate moveDate) {
        final Map<Long, User> userMap = userQueryService.findUserMapByIds(registerQueryService::findUserIdsByCategoryId, categoryId);
        final Map<Todo, List<Register>> groupByTodo = getTodoMap(categoryId, moveDate);
        final ArrayList<CategorySubTodoResponse> todoMainRespons = new ArrayList<>();
        for (Todo todo : groupByTodo.keySet()) {
            final List<Register> registers = groupByTodo.get(todo);
            ArrayList<AssignUserInfoResponse> assignUserInfoRespons = new ArrayList<>();
            for (Register register : registers) {
                final User findUser = userMap.get(register.getUserId());
                assignUserInfoRespons.add(new AssignUserInfoResponse(findUser.getId(), findUser.getNickname()));
            }
            todoMainRespons.add(new CategorySubTodoResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name(), assignUserInfoRespons));
        }
        return todoMainRespons;
    }

    private Map<Todo, List<Register>> getTodoMap(Long categoryId, LocalDate moveDate) {
        return assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, moveDate)
            .stream()
            .collect(Collectors.groupingBy(Assign::getTodo, LinkedHashMap::new,Collectors.mapping(Assign::getRegister, Collectors.toList())));
    }
}
