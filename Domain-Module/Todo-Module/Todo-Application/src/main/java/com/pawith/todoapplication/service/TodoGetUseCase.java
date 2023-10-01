package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.AssignSimpleInfoResponse;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.TodoListResponse;
import com.pawith.todoapplication.dto.response.TodoSimpleResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.CategoryQueryService;
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
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final CategoryQueryService categoryQueryService;
    private final UserQueryService userQueryService;
    private final RegisterQueryService registerQueryService;


    public SliceResponse<TodoHomeResponse> getTodos(final Long todoTeamId, final Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice <Todo> todoList = todoQueryService.findTodayTodoSlice(user.getId(), todoTeamId, pageable);
        Slice<TodoHomeResponse> todoHomeResponseSlice = todoList.map(todo -> new TodoHomeResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name()));
        return SliceResponse.from(todoHomeResponseSlice);
    }

    public List<TodoListResponse> getTodoList(Long todoTeamId, LocalDate moveDate){
        final List<Category> categories = categoryQueryService.findCategoryListByTodoTeamId(todoTeamId);
        return categories.stream()
            .map(category -> {
                final List<Todo> todoList = todoQueryService.findTodoListByCategoryIdAndscheduledDate(category.getId(), moveDate);
                List<TodoSimpleResponse> todoSimpleResponses = todoList.stream()
                    .map(todo -> {
                        final List<Register> registers = registerQueryService.findAllRegisterByTodoId(todo.getId());
                        final List<AssignSimpleInfoResponse> assignSimpleInfoResponses = registers.stream()
                            .map(register -> {
                                final User findUser = userQueryService.findById(register.getUserId());
                                return new AssignSimpleInfoResponse(findUser.getId(), findUser.getNickname());
                            })
                            .collect(Collectors.toList());
                        return new TodoSimpleResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name(), assignSimpleInfoResponses);
                    })
                    .collect(Collectors.toList());

                return new TodoListResponse(category.getName(), todoSimpleResponses);
            })
            .collect(Collectors.toList());
    }
}
