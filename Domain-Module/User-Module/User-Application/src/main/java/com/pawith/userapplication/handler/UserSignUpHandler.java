package com.pawith.userapplication.handler;

import com.pawith.commonmodule.event.UserSignUpEvent;
import com.pawith.userapplication.mapper.UserMapper;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserAuthoritySaveService;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.service.UserSaveService;
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
