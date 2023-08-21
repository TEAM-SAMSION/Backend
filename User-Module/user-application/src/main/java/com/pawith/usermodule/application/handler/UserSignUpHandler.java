package com.pawith.usermodule.application.handler;

import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.domain.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UserSignUpHandler {

    private final UserSaveService userSaveService;

    @Transactional
    @EventListener
    public void signUp(UserSignUpEvent userSignUpEvent){
        final User user = UserMapper.toEntity(userSignUpEvent);
        userSaveService.saveUser(user);
    }

}
