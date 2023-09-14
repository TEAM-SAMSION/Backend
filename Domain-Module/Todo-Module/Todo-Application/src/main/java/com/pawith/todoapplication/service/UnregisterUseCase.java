package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterDeleteService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@Transactional
@RequiredArgsConstructor
public class UnregisterUseCase {
    private final RegisterQueryService registerQueryService;
    private final RegisterDeleteService registerDeleteService;

    public void unregisterTodoTeam(final Long todoTeamId) {
        final User user = UserUtils.getAccessUser();
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, user.getId());
        registerDeleteService.deleteRegisterByTodoTeamId(register);
    }
}
