package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.UnregistrableException;
import com.pawith.tododomain.repository.RegisterRepository;
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
            if (presidentRegisterCount <= 1) {
                throw new UnregistrableException(Error.CANNOT_PRESIDENT_UNREGISTRABLE);
            }
        }
    }

    public void validatePresidentRegisterListDeletable(final List<Register> registerList){
        final List<Long> todoTeamIds = registerList.stream()
            .map(register -> register.getTodoTeam().getId())
            .collect(Collectors.toList());
        final List<Integer> presidentRegisterCountList = registerRepository.countAllByTodoTeamIdsInAndAuthority(todoTeamIds, Authority.PRESIDENT);
        final boolean isNotUnregistrable = presidentRegisterCountList.stream()
            .anyMatch(presidentRegisterCount -> presidentRegisterCount <= 1);
        if (isNotUnregistrable) {
            throw new UnregistrableException(Error.CANNOT_PRESIDENT_UNREGISTRABLE);
        }
    }
}