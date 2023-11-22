package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoDetailResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameResponse;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSearchInfoResponse;
import com.pawith.todoapplication.dto.response.WithdrawTodoTeamResponse;
import com.pawith.todoapplication.mapper.TodoTeamMapper;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.PetQueryService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoTeamGetUseCase {

    private final UserUtils userUtils;
    private final RegisterQueryService registerQueryService;
    private final TodoTeamQueryService todoTeamQueryService;
    private final UserQueryService userQueryService;
    private final PetQueryService petQueryService;


    public SliceResponse<TodoTeamInfoResponse> getTodoTeams(final Pageable pageable) {
        final User requestUser = userUtils.getAccessUser();
        final Slice<TodoTeamInfoResponse> todoTeamSimpleResponses =
            registerQueryService.findRegisterSliceByUserId(requestUser.getId(), pageable)
                .map(register -> TodoTeamMapper.mapToTodoTeamSimpleResponse(register.getTodoTeam(), register));
        return SliceResponse.from(todoTeamSimpleResponses);
    }

    public ListResponse<TodoTeamNameResponse> getTodoTeamName() {
        final User requestUser = userUtils.getAccessUser();
        final List<TodoTeamNameResponse> todoTeamNameResponses =
            registerQueryService.findRegisterListByUserIdWithTodoTeam(requestUser.getId())
                .stream()
                .map(register -> TodoTeamMapper.mapToTodoTeamNameResponse(register.getTodoTeam(), register))
                .collect(Collectors.toList());
        return ListResponse.from(todoTeamNameResponses);
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

    public ListResponse<WithdrawTodoTeamResponse> getWithdrawTodoTeam() {
        User requestUser = userUtils.getAccessUser();
        List<TodoTeam> todoTeamList = todoTeamQueryService.findAllTodoTeamByUserId(requestUser.getId());
        return ListResponse.from(
                todoTeamList.stream()
                        .map(todoTeam -> new WithdrawTodoTeamResponse(todoTeam.getImageUrl(), todoTeam.getTeamName()))
                        .collect(Collectors.toList())
        );
    }

    public TodoTeamInfoDetailResponse getTodoTeamInfo(Long todoTeamId) {
        TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(todoTeamId);
        Integer registerCount = registerQueryService.countRegisterByTodoTeamId(todoTeamId);
        Integer petCount = petQueryService.countPetByTodoTeamId(todoTeamId);
        return TodoTeamMapper.mapToTodoTeamInfoDetailResponse(todoTeam, registerCount, petCount);
    }

    public TodoTeamNameResponse getTodoTeamLatest() {
        final User requestUser = userUtils.getAccessUser();
        final Register register = registerQueryService.findLatestTodoTeam(requestUser.getId());
        return TodoTeamMapper.mapToTodoTeamNameResponse(register.getTodoTeam(), register);
    }
}
