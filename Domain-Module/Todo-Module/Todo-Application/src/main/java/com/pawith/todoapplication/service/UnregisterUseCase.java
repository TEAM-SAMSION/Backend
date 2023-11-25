package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.RegisterValidateService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class UnregisterUseCase {
    private final UserUtils userUtils;
    private final RegisterQueryService registerQueryService;
    private final RegisterValidateService registerValidateService;

    public void unregisterTodoTeam(final Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, user.getId());
        register.unregister();
    }

    public void validateRegisterDeletable(final Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, user.getId());
        registerValidateService.validatePresidentRegisterDeletable(register);
    }

    public void validateRegistersDeletable() {
        final User user = userUtils.getAccessUser();
        final List<Register> registers = registerQueryService.findAllRegistersByUserId(user.getId());
        registerValidateService.validateRegisterDeletable(registers);
    }
}
