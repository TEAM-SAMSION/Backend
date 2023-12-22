package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.RegisterInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterManageInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterSearchInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterTermResponse;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashMap;
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
        Comparator<Authority> authorityComparator = Comparator.comparing(Enum::ordinal);
        final List<RegisterManageInfoResponse> manageRegisterInfoResponses = allRegisters.stream()
                .sorted(Comparator.comparing(register -> register.getAuthority(), authorityComparator))
                .map(register -> {
                    final User registerUser = registerUserMap.get(register.getUserId());
                    return new RegisterManageInfoResponse(register.getId(), register.getAuthority(), registerUser.getNickname(), registerUser.getEmail(), registerUser.getImageUrl());
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

    public ListResponse<RegisterSearchInfoResponse> searchRegisterByNickname(final Long todoTeamId, final String nickname) {
        final List<Register> registers = registerQueryService.findAllRegistersByTodoTeamId(todoTeamId);
        final List<Long> registerUserIds = registers.stream()
            .map(Register::getUserId)
            .collect(Collectors.toList());
        final List<User> users = userQueryService.findAllByNicknameAndIds(nickname, registerUserIds);
        System.out.println(users);
        final Map<User, Register> userRegisterMap = getRegisterUserMap(registers, users);
        final List<RegisterSearchInfoResponse> registerSearchInfoResponses = users.stream()
                .map(user -> {
                    Register register = userRegisterMap.get(user);
                    return new RegisterSearchInfoResponse(register.getId(), register.getAuthority(), user.getNickname(), user.getEmail(), user.getImageUrl());
                })
                .collect(Collectors.toList());
        return ListResponse.from(registerSearchInfoResponses);
    }

    private Map<User, Register> getRegisterUserMap(List<Register> registers, List<User> users) {
        Comparator<Authority> authorityComparator = Comparator.comparing(Enum::ordinal);

        List<Register> sortedRegisters = registers.stream()
                .sorted(Comparator.comparing(Register::getAuthority, authorityComparator))
                .toList();

        return users.stream()
                .flatMap(user -> sortedRegisters.stream()
                        .filter(register -> user.getId().equals(register.getUserId()))
                        .map(register -> Map.entry(user, register)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, LinkedHashMap::new));
    }
}
