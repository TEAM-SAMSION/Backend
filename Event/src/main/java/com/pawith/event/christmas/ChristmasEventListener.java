package com.pawith.event.christmas;

import com.pawith.commonmodule.event.ChristmasEvent;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.TodoTeamQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChristmasEventListener {

    private final ChristmasEventService christmasEventService;
    private final TodoTeamQueryService todoTeamQueryService;

    @EventListener
    public void handleEventCreateNewTodoTeam(ChristmasEvent.ChristmasEventCreateNewTodoTeam event) {
        final TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(event.todoTeamId());
        christmasEventService.publishEvent(todoTeam);
    }

    @EventListener
    public void handleEventCreateNewRegister(ChristmasEvent.ChristmasEventCreateNewRegister event) {
        christmasEventService.addNewRegisterAtChristmasEventTodo(event.todoTeamId(), event.userId());
    }
}
