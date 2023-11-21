package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoCountResponse;
import com.pawith.todoapplication.dto.response.WithdrawAllTodoResponse;
import com.pawith.todoapplication.dto.response.WithdrawTodoResponse;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoWithdrawGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;


    public SliceResponse<WithdrawTodoResponse> getWithdrawTeamTodoList(Long todoTeamId, final Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice<WithdrawTodoResponse> withdrawTodoResponses =
            todoQueryService.findAllTodoListByTodoTeamId(user.getId(), todoTeamId, pageable)
                .map(todo -> new WithdrawTodoResponse(todo.getCategory().getId(), todo.getCategory().getName(), todo.getDescription()));
        return SliceResponse.from(withdrawTodoResponses);
    }

    public SliceResponse<WithdrawAllTodoResponse> getWithdrawTodoList(Pageable pageable) {
        final User user = userUtils.getAccessUser();
        final Slice<WithdrawAllTodoResponse> withdrawAllTodoResponses =
            todoQueryService.findAllTodoListByUserId(user.getId(), pageable)
                .map(todo -> new WithdrawAllTodoResponse(todo.getCategory().getTodoTeam().getImageUrl(), todo.getCategory().getName(), todo.getDescription()));
        return SliceResponse.from(withdrawAllTodoResponses);
    }

    public TodoCountResponse getWithdrawTeamTodoCount(Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final Integer todoCount = todoQueryService.countTodoByTodoTeamId(user.getId(), todoTeamId);
        return new TodoCountResponse(todoCount);
    }

    public TodoCountResponse getWithdrawTodoCount() {
        final User user = userUtils.getAccessUser();
        final Integer todoCount = todoQueryService.countTodoByUserId(user.getId());
        return new TodoCountResponse(todoCount);
    }
}
