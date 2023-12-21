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

    public void validateTodoDeletable(Todo todo, Register register) {
        if(!todo.isTodoCreator(register.getId()) && register.isMember()){
            throw new TodoModificationNotAllowedException(TodoError.TODO_MODIFICATION_NOT_ALLOWED);
        }
    }
}
