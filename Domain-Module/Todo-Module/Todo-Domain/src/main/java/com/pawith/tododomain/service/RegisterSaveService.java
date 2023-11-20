package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.AlreadyRegisterTodoTeamException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class RegisterSaveService {
    private final RegisterRepository registerRepository;

    public void saveRegisterAboutMember(TodoTeam todoTeam, Long userId) {
        if(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId, false)){
            registerRepository.changeIsRegisteredWhenRegisterAlreadyExist(todoTeam.getId(), userId);
            return;
        }
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
        if(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId, true)){
            throw new AlreadyRegisterTodoTeamException(TodoError.ALREADY_REGISTER_TODO_TEAM);
        }
    }
}
