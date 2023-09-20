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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final RegisterQueryService registerQueryService;
    private final AssignQueryService assignQueryService;
    public SliceResponse<TodoHomeResponse> getTodos(final Long teamId, final Pageable pageable) {
        final LocalDate serverTime = LocalDate.now();
        final User user = userUtils.getAccessUser();
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(teamId, user.getId());
        final Slice<Assign> assignList = assignQueryService.findAssignSliceByRegisterIdAndCreatedAtBetween(register.getId(), serverTime.atStartOfDay(), serverTime.atTime(LocalTime.MAX), pageable);

        Slice<TodoHomeResponse> todoHomeResponseSlice = assignList.map(assign -> {
            final Todo todo = todoQueryService.findTodoByTodoId(assign.getTodo().getId());
            return new TodoHomeResponse(todo.getId(), todo.getDescription(), todo.getTodoStatus().name());
        });
        return SliceResponse.from(todoHomeResponseSlice);
    }
}
