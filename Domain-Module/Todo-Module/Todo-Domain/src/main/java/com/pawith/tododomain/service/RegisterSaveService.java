package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.AlreadyRegisterTodoTeamException;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class RegisterSaveService {
    private final RegisterRepository registerRepository;

    public void saveRegisterAboutMember(TodoTeam todoTeam, Long userId) {
        saveRegisterEntity(todoTeam, userId, Authority.MEMBER);
    }

    public void saveRegisterAboutPresident(TodoTeam todoTeam, Long userId) {
        saveRegisterEntity(todoTeam, userId, Authority.PRESIDENT);
    }

    private void saveRegisterEntity(TodoTeam todoTeam, Long userId, Authority authority) {
        checkAlreadyRegisterTodoTeam(todoTeam, userId);
        registerRepository.save(Register.builder()
                .todoTeam(todoTeam)
                .userId(userId)
                .authority(authority)
                .build());
    }

    private void checkAlreadyRegisterTodoTeam(TodoTeam todoTeam, Long userId) {
        if(registerRepository.existsByTodoTeamIdAndUserId(todoTeam.getId(), userId)){
            throw new AlreadyRegisterTodoTeamException(Error.ALREADY_REGISTER_TODO_TEAM);
        }
    }
}