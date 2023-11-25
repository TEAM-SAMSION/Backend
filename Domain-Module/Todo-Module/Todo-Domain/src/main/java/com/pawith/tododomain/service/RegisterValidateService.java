package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.UnchangeableException;
import com.pawith.tododomain.exception.UnregistrableException;
import com.pawith.tododomain.repository.RegisterRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
@Transactional
public class RegisterValidateService {

    private final RegisterRepository registerRepository;

    public void validatePresidentRegisterDeletable(final Register register) {
        if (register.isPresident()) {
            final TodoTeam todoTeam = register.getTodoTeam();
            final Integer presidentRegisterCount = registerRepository.countByTodoTeamIdAndAuthority(todoTeam.getId(), Authority.PRESIDENT);
            final Integer registerCount = registerRepository.countByTodoTeamId(todoTeam.getId());
            if (presidentRegisterCount <= 1 && registerCount > 1) {
                throw new UnregistrableException(TodoError.CANNOT_PRESIDENT_UNREGISTRABLE);
            }
        }
    }

    public void validateRegisterDeletable(final List<Register> registerList) {
        final Map<Register, Integer> registerCountMap = registerList.stream()
                .collect(Collectors.toMap(register -> register, register -> registerRepository.countByTodoTeamId(register.getTodoTeam().getId())));
        final boolean isPresidentExist = !registerList.isEmpty() && registerList.stream()
                .filter(Register::isPresident)
                .allMatch(register -> registerCountMap.get(register) > 1);
        if (isPresidentExist) {
            throw new UnregistrableException(TodoError.CANNOT_PRESIDENT_UNREGISTRABLE);
        }
    }

    public void validateAuthorityChangeable(final Register userRegister, final Authority authority) {
        if (!userRegister.isPresident() && authority.equals(Authority.PRESIDENT)) {
            throw new UnchangeableException(TodoError.CANNOT_CHANGE_AUTHORITY);
        }
    }
}
