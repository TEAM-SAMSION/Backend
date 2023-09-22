package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
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
    private final RegisterQueryService registerQueryService;
    private final AssignQueryService assignQueryService;

    /**
     * 성능개선 전 , 100회 테스트 평균 : 915ms
     *
     */
    public SliceResponse<TodoHomeResponse> getTodos(final Long todoTeamId, final Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice <Todo> todoList = todoQueryService.findTodayTodoSlice(user.getId(), todoTeamId, pageable);
        Slice<TodoHomeResponse> todoHomeResponseSlice = todoList.map(todo -> new TodoHomeResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name()));
        return SliceResponse.from(todoHomeResponseSlice);
    }
}
