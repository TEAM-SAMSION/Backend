package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.todoapplication.dto.response.AssignUserInfoResponse;
import com.pawith.todoapplication.dto.response.TodoCompletionResponse;
import com.pawith.todoapplication.dto.response.TodoInfoResponse;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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


    public ListResponse<CategorySubTodoResponse> getTodoListByCategoryId(Long categoryId, LocalDate moveDate) {
        final List<Register> registers = registerQueryService.findAllRegistersByCategoryId(categoryId);
        Map<Long, Register> registerMap = listToMap(registers, Register::getId);
        List<Long> userIds = listToList(registers, Register::getUserId);
        final Map<Long, User> userMap = userQueryService.findUserMapByIds(userIds);
        final Map<Todo, List<Assign>> groupByTodo = getTodoMap(categoryId, moveDate);
        final ArrayList<CategorySubTodoResponse> todoMainResponses = new ArrayList<>();
        for (Todo todo : groupByTodo.keySet()) {
            final List<Assign> assigns = groupByTodo.get(todo);
            ArrayList<AssignUserInfoResponse> assignUserInfoResponses = new ArrayList<>();
            for (Assign assign : assigns) {
                final Register register = registerMap.get(assign.getRegister().getId());
                final User findUser = userMap.get(register.getUserId());
                assignUserInfoResponses.add(new AssignUserInfoResponse(findUser.getId(), findUser.getNickname(), assign.getCompletionStatus()));
            }
            todoMainResponses.add(new CategorySubTodoResponse(todo.getId(), todo.getDescription(), todo.getCompletionStatus(), assignUserInfoResponses));
        }
        return ListResponse.from(todoMainResponses);
    }

    public TodoCompletionResponse getTodoCompletion(Long todoId) {
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        return new TodoCompletionResponse(todo.getCompletionStatus());
    }

    private Map<Todo, List<Assign>> getTodoMap(Long categoryId, LocalDate moveDate) {
        return assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, moveDate)
                .stream()
                .collect(Collectors.groupingBy(Assign::getTodo, LinkedHashMap::new,Collectors.mapping(Function.identity(), Collectors.toList())));
    }

    private static <T, K> Map<K, T> listToMap(List<T> list, Function<T, K> keyExtractor) {
        return list.stream()
                .collect(Collectors.toMap(keyExtractor, Function.identity()));
    }

    private static <T, K> List<K> listToList(List<T> list, Function<T, K> mapper) {
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

}
