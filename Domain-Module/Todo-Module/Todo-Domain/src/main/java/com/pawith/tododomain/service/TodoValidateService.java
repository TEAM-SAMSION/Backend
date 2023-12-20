package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.TodoModificationNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoValidateService {

    public boolean validateDeleteAndUpdate(Todo todo, Register register) {
        return !todo.isTodoCreator(register.getId()) && register.isMember();
    }
}
