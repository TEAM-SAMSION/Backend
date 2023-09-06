package com.pawith.usermodule.application.handler;

import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.application.mapper.UserMapper;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.service.UserAuthoritySaveService;
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
    private final UserQueryService userQueryService;
    private final UserAuthoritySaveService userAuthoritySaveService;

    @Transactional
    @EventListener
    public void signUp(final UserSignUpEvent userSignUpEvent){
        if(!userQueryService.checkEmailAlreadyExist(userSignUpEvent.getEmail())) {
            final User user = UserMapper.toEntity(userSignUpEvent);
            userSaveService.saveUser(user);
            userAuthoritySaveService.saveUserAuthority(userSignUpEvent.getEmail());
        }
        else {
            userQueryService.checkAccountAlreadyExist(userSignUpEvent.getEmail(), userSignUpEvent.getProvider());
        }
    }

}
