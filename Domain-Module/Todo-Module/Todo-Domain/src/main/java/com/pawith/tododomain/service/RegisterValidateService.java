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

    public void validatePresidentRegisterDeletable(final Register register) {
        if (register.isPresident()) {
            final TodoTeam todoTeam = register.getTodoTeam();
            final Integer registerCount = registerRepository.countByTodoTeamIdQuery(todoTeam.getId());
            if (registerCount > 1) {
                throw new UnregistrableException(TodoError.CANNOT_PRESIDENT_UNREGISTRABLE);
            }
        }
    }

    public void validateRegisterDeletable(final List<Register> registerList) {
        registerList.forEach(register -> {
            final Long todoTeamId = register.getTodoTeam().getId();
            final Integer registerCount = registerRepository.countByTodoTeamIdQuery(todoTeamId);
            if (register.isPresident() && registerCount > 1) {
                throw new UnregistrableException(TodoError.CANNOT_PRESIDENT_UNREGISTRABLE);
            }
        });
    }

    public void validateAuthorityChangeable(final Register userRegister, final Authority authority) {
        if (!userRegister.isPresident() && authority.equals(Authority.PRESIDENT)) {
            throw new UnchangeableException(TodoError.CANNOT_CHANGE_AUTHORITY);
        }
    }
}
