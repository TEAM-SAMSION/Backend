package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameResponse;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSearchInfoResponse;
import com.pawith.todoapplication.mapper.TodoTeamMapper;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoTeamGetUseCase {

    private final UserUtils userUtils;
    private final RegisterQueryService registerQueryService;
    private final TodoTeamQueryService todoTeamQueryService;
    private final UserQueryService userQueryService;


    public SliceResponse<TodoTeamInfoResponse> getTodoTeams(final Pageable pageable) {
        final User requestUser = userUtils.getAccessUser();
        final Slice<TodoTeamInfoResponse> todoTeamSimpleResponses =
            registerQueryService.findRegisterSliceByUserId(requestUser.getId(), pageable)
                .map(register -> TodoTeamMapper.mapToTodoTeamSimpleResponse(register.getTodoTeam(), register));
        return SliceResponse.from(todoTeamSimpleResponses);
    }

    public ListResponse<TodoTeamNameResponse> getTodoTeamName() {
        final User requestUser = userUtils.getAccessUser();
        return ListResponse.from(
                todoTeamQueryService.findAllTodoTeamByUserId(requestUser.getId())
                .stream()
                .map(todoTeam -> new TodoTeamNameResponse(todoTeam.getId(), todoTeam.getTeamName()))
                .collect(Collectors.toList())
        );
    }

    public TodoTeamSearchInfoResponse searchTodoTeamByCode(final String code) {
        final TodoTeam todoTeam = todoTeamQueryService.findTodoTeamByCode(code);
        final Register presidentRegister = registerQueryService.findPresidentRegisterByTodoTeamId(todoTeam.getId());
        final User presidentUser = userQueryService.findById(presidentRegister.getUserId());
        final Integer registerCount = registerQueryService.countRegisterByTodoTeamId(todoTeam.getId());
        return TodoTeamMapper.mapToTodoTeamSearchInfoResponse(todoTeam, presidentUser, registerCount);
    }

    public TodoTeamRandomCodeResponse getTodoTeamCode(Long teamId) {
        TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(teamId);
        return TodoTeamMapper.mapToTodoTeamRandomCodeResponse(todoTeam);
    }
}
