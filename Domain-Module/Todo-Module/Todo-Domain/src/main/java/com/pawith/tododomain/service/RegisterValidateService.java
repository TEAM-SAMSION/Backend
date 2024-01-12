package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.UnchangeableException;
import com.pawith.tododomain.exception.UnregistrableException;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional
public class RegisterValidateService {

    private final RegisterRepository registerRepository;

    public boolean validatePresidentRegisterDeletable(final Register register) {
        return register.isPresident() && registerCountGreaterThanOne(register);
    }

    public boolean validateRegisterDeletable(final List<Register> registerList) {
        return registerList.stream()
                .anyMatch(register -> register.isPresident() && registerCountGreaterThanOne(register));
    }

    private boolean registerCountGreaterThanOne(Register register) {
        final Long todoTeamId = register.getTodoTeam().getId();
        final Integer registerCount = registerRepository.countByTodoTeamIdQuery(todoTeamId);
        return registerCount > 1;
    }

    public void validateAuthorityChangeable(final Register userRegister, final Authority authority) {
        if (!userRegister.isPresident() && authority.equals(Authority.PRESIDENT)) {
            throw new UnchangeableException(TodoError.CANNOT_CHANGE_AUTHORITY);
        }
    }
}
