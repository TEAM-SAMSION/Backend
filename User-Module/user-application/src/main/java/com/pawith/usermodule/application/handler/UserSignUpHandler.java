package com.pawith.usermodule.application.handler;

import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.application.mapper.UserMapper;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.repository.UserRepository;
import com.pawith.usermodule.domain.service.UserQueryService;
import com.pawith.usermodule.domain.service.UserSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSignUpHandler {

    private final UserSaveService userSaveService;
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    @Transactional
    @EventListener
    public void signUp(UserSignUpEvent userSignUpEvent){
        if(!userRepository.existsByEmail(userSignUpEvent.getEmail())) {
            final User user = UserMapper.toEntity(userSignUpEvent);
            userSaveService.saveUser(user);
        }
        else {
            userQueryService.checkAccountAlreadyExist(userSignUpEvent.getEmail(), userSignUpEvent.getProvider());
        }
    }

}
