package com.pawith.usermodule.handler;

import com.pawith.usermodule.handler.event.UserSignUpEvent;
import com.pawith.usermodule.mapper.UserMapper;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.service.UserAuthoritySaveService;
import com.pawith.usermodule.service.UserQueryService;
import com.pawith.usermodule.service.UserSaveService;
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
