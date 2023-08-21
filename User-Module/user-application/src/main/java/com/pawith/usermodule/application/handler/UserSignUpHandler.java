package com.pawith.usermodule.application.handler;

import com.pawith.commonmodule.exception.Error;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.application.mapper.UserMapper;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.repository.UserRepository;
import com.pawith.usermodule.domain.service.UserSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserSignUpHandler {

    private final UserSaveService userSaveService;
    private final UserRepository userRepository;

    @Transactional
    @EventListener
    public void signUp(UserSignUpEvent userSignUpEvent){
        if(!userRepository.existsByEmail(userSignUpEvent.getEmail())) {
            final User user = UserMapper.toEntity(userSignUpEvent);
            userSaveService.saveUser(user);
        }
        else
            throw new IllegalArgumentException("사용자 존재");
    }

}
