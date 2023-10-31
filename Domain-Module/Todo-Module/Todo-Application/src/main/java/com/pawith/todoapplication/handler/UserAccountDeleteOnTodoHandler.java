package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.event.UserAccountDeleteEvent;
import com.pawith.tododomain.service.AssignDeleteService;
import com.pawith.tododomain.service.RegisterDeleteService;
import com.pawith.tododomain.service.RegisterQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserAccountDeleteOnTodoHandler {
    private final RegisterQueryService registerQueryService;
    private final RegisterDeleteService registerDeleteService;
    private final AssignDeleteService assignDeleteService;

    @EventListener
    public void deleteUserInfo(UserAccountDeleteEvent userAccountDeleteEvent){
        final List<Long> registerIds = registerQueryService.findRegisterIdsByUserId(userAccountDeleteEvent.getUserId());
        log.info("registerIds: {}", registerIds);
        assignDeleteService.deleteAssignByRegisterId(registerIds);
        registerDeleteService.deleteRegisterByRegisterIds(registerIds);
    }
}
