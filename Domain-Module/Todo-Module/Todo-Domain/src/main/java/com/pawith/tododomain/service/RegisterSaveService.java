package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.event.ChristmasEvent;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.AlreadyRegisterTodoTeamException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class RegisterSaveService {
    private final RegisterRepository registerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void saveRegisterAboutMember(TodoTeam todoTeam, Long userId) {
        saveRegisterEntity(todoTeam, userId, Authority.MEMBER);
    }

    public void saveRegisterAboutPresident(TodoTeam todoTeam, Long userId) {
        saveRegisterEntity(todoTeam, userId, Authority.PRESIDENT);
    }

    private void saveRegisterEntity(TodoTeam todoTeam, Long userId, Authority authority) {
        checkAlreadyRegisterTodoTeam(todoTeam, userId);
        registerRepository.findByTodoTeamIdAndUserId(todoTeam.getId(), userId)
                .ifPresentOrElse(register -> {
                    register.updateAuthority(authority);
                    register.reRegister();
                }, () -> {
                    registerRepository.save(Register.builder()
                            .todoTeam(todoTeam)
                            .userId(userId)
                            .authority(authority)
                            .build());
                });
        applicationEventPublisher.publishEvent(new ChristmasEvent.ChristmasEventCreateNewRegister(todoTeam.getId(), userId));
    }

    private void checkAlreadyRegisterTodoTeam(TodoTeam todoTeam, Long userId) {
        if(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId, true)){
            throw new AlreadyRegisterTodoTeamException(TodoError.ALREADY_REGISTER_TODO_TEAM);
        }
    }
}
