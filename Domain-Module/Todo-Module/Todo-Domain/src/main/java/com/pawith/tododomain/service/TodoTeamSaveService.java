package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TodoTeamSaveService {

    private final TodoTeamRepository todoTeamRepository;

    public void saveTodoTeamEntity(TodoTeam todoTeam){
        todoTeamRepository.save(todoTeam);
    }
}
