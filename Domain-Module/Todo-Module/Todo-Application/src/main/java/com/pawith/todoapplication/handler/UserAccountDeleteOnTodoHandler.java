package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.event.UserAccountDeleteEvent;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.AssignDeleteService;
import com.pawith.tododomain.service.RegisterDeleteService;
import com.pawith.tododomain.service.RegisterQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserAccountDeleteOnTodoHandler {
    private final RegisterQueryService registerQueryService;
    private final RegisterDeleteService registerDeleteService;
    private final AssignDeleteService assignDeleteService;

    @EventListener
    public void deleteUserInfo(UserAccountDeleteEvent userAccountDeleteEvent) {
        final List<Register> registers = registerQueryService.findAllRegistersByUserId(userAccountDeleteEvent.userId());
        final List<Long> registerIds = registers.stream()
            .map(Register::getId)
            .collect(Collectors.toList());
        assignDeleteService.deleteAssignByRegisterId(registerIds);
        registerDeleteService.deleteRegisterByRegisterIds(registerIds);
    }
}
