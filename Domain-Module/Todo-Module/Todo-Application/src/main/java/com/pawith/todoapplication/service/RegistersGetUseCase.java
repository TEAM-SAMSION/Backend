package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistersGetUseCase {

    private final UserUtils userUtils;
    private final RegisterQueryService registerQueryService;
    private final UserQueryService userQueryService;

    public ListResponse<RegisterInfoResponse> getRegisters(final Long teamId) {
        final List<Register> allRegisters = registerQueryService.findAllRegistersByTodoTeamId(teamId);
        final Map<Long, User> registerUserMap = getRegisterUserMap(allRegisters);
        final List<RegisterInfoResponse> registerSimpleInfoResponses = allRegisters.stream()
            .map(register -> {
                final User registerUser = registerUserMap.get(register.getUserId());
                return new RegisterInfoResponse(register.getId(), registerUser.getNickname());
            })
            .collect(Collectors.toList());
        return ListResponse.from(registerSimpleInfoResponses);
    }

    public ListResponse<RegisterManageInfoResponse> getManageRegisters(final Long teamId) {
        final List<Register> allRegisters = registerQueryService.findAllRegistersByTodoTeamId(teamId);
        final Map<Long, User> registerUserMap = getRegisterUserMap(allRegisters);
        final List<RegisterManageInfoResponse> manageRegisterInfoResponses = allRegisters.stream()
                .map(register -> {
                    final User registerUser = registerUserMap.get(register.getUserId());
                    return new RegisterManageInfoResponse(register.getId(), register.getAuthority().toString(), registerUser.getNickname(), registerUser.getEmail());
                })
                .collect(Collectors.toList());
        return ListResponse.from(manageRegisterInfoResponses);
    }

    public RegisterTermResponse getRegisterTerm(final Long teamId) {
        final User user = userUtils.getAccessUser();
        final Integer registerTerm = registerQueryService.findUserRegisterTerm(teamId, user.getId());
        return new RegisterTermResponse(registerTerm);
    }


    private Map<Long, User> getRegisterUserMap(List<Register> allRegisters) {
        final List<Long> registerUserIds = allRegisters.stream()
            .map(Register::getUserId)
            .collect(Collectors.toList());
        return userQueryService.findUserMapByIds(registerUserIds);
    }
}
