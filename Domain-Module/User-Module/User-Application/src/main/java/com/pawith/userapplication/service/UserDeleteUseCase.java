package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.event.UserAccountDeleteEvent;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserDeleteService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class UserDeleteUseCase {
    private final UserUtils userUtils;
    private final UserDeleteService userDeleteService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void deleteUser(){
        final User user = userUtils.getAccessUser();
        userDeleteService.deleteUser(user.getId());
        applicationEventPublisher.publishEvent(new UserAccountDeleteEvent(user.getId()));
    }
}
