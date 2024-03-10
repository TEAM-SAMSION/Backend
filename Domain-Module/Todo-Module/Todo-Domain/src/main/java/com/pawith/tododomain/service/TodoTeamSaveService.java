package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.event.ChristmasEvent;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class TodoTeamSaveService {

    private final TodoTeamRepository todoTeamRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void saveTodoTeamEntity(TodoTeam todoTeam){
        final TodoTeam savedTodoTeam = todoTeamRepository.save(todoTeam);
        applicationEventPublisher.publishEvent(new ChristmasEvent.ChristmasEventCreateNewTodoTeam(savedTodoTeam.getId()));
    }
}
